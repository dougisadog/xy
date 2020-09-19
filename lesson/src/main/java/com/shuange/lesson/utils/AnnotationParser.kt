package com.shuange.lesson.utils

import android.util.Log
import androidx.fragment.app.Fragment
import com.shuange.lesson.base.BaseFragment
import corelib.http.HttpTask
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.javaField
import corelib.extension.removeLast


/**
 * Parser for custom annotation
 */
object AnnotationParser {

    /**
     * generate params by baseRequest, and bind to api
     */
    fun generateParams(api: HttpTask<*>, baseRequest: Any) {
        api.queryItems.clear()

        var tempClass: KClass<*>? = baseRequest::class
        while (null != tempClass) {
            //get real kotlin members
            if (!tempClass.java.isInterface) {
                val fields = tempClass.declaredMemberProperties
                val isRequestClass = tempClass.java.isAnnotationPresent(RequestClass::class.java)
                fields.forEach {
                    //get annotation by java field instead of kotlin member
                    val javaField = it.javaField ?: return@forEach
                    //check Annotation
                    if ((isRequestClass || javaField.isAnnotationPresent(RequestParam::class.java))
                        && !javaField.isAnnotationPresent(RequestIgnoreParam::class.java)
                    ) {
                        bindField(javaField, api, baseRequest)
                    }
                }
            }
            tempClass =
                if (tempClass.superclasses.isNotEmpty()) tempClass.superclasses.first() else null
        }
    }

    /**
     * set field value to api
     */
    private fun bindField(it: Field, api: HttpTask<*>, baseRequest: Any) {
        var requestKey = it.name
        if (it.isAnnotationPresent(RequestParam::class.java)) {
            val targetKey = it.getAnnotation(RequestParam::class.java)?.name
            if (!targetKey.isNullOrEmpty()) {
                requestKey = targetKey
            }
        }
        it.isAccessible = true
        val value = try {
            it.get(baseRequest)
        } catch (e: Exception) {
            Log.e(baseRequest::class.java.name, e.toString())
        }
        if (null != value) {
            api.addQuery(requestKey, value)
        }
    }

    /**
     * check if fragment can be cancel by back press
     */
    private fun checkFragmentCancelable(fragment: Fragment): Boolean {
        val classOf = fragment::class.java
        val handled = if (fragment is BaseFragment<*, *>) fragment.onBackPressed() else true
        return classOf.isAnnotationPresent(Cancelable::class.java) && handled
    }

    /**
     * loop all child node fragment
     */
    fun nextFragment(
        fragment: Fragment,
        condition: (Fragment) -> Boolean = this::checkFragmentCancelable
    ): Fragment? {
        val childFragments = mutableListOf<Fragment>()
        childFragments.addAll(fragment.childFragmentManager.fragments)
        while (childFragments.size > 0) {
            val next = childFragments.last()

            val target = nextFragment(next)
            if (null != target) {
                return target
            }
            if (condition(next)) {
                return next
            } else {
                childFragments.removeLast()
            }
        }
        if (condition(fragment)) {
            return fragment
        }
        return null
    }

    /**
     * find cancelable child fragment
     */
    fun findCancelableFragment(fragment: Fragment): Fragment? {
        val childFragments = mutableListOf<Fragment>()
        childFragments.addAll(fragment.childFragmentManager.fragments)
        while (childFragments.size > 0) {
            val fragment = childFragments.last()

            val classOf = fragment::class.java
            if (classOf.isAnnotationPresent(FragmentCancelable::class.java)) {
                return fragment
            } else {
                childFragments.removeLast()
            }
        }

        return null
    }
}

/**
 * RequestClass Annotation
 */
@Target(AnnotationTarget.CLASS)
annotation class RequestClass

/**
 * RequestParam Annotation
 */
@Target(AnnotationTarget.FIELD)
annotation class RequestParam(
    /**
     * request key name
     * @return
     */
    val name: String = ""

)

/**
 * RequestIgnoreParam Annotation
 */
@Target(AnnotationTarget.FIELD)
annotation class RequestIgnoreParam

/**
 * Cancelable Annotation
 */
@Target(AnnotationTarget.CLASS)
annotation class Cancelable

/**
 * FragmentCancelable Annotation
 */
@Target(AnnotationTarget.CLASS)
annotation class FragmentCancelable