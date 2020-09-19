package corelib.convert

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonParser : Parser {

    override fun <T> parse(src: String, clazz: Class<T>): T? {
        return this.jsonParse(src, clazz)
    }

    override fun <T> parseList(src: String, clazz: Class<T>): List<T>? {
        return this.jsonListParse(src)
    }

    private fun <T> jsonListParse(json: String): List<T>? {
        val listType = object : TypeToken<List<T>>() {}.type
        var mc: List<T>? = null
        try {
            mc = Gson().fromJson(json, listType)
        } catch (e: Exception) {
        }
        return mc
    }

    fun getJsonString(o: Any): String? {
        var json: String? = null
        try {
            json = Gson().toJson(o)
        } catch (e: Exception) {
            e.printStackTrace()
            return json
        }

        return json
    }

    private fun <T> jsonParse(json: String, clazz: Class<T> ): T? {
        var mc: T? = null
        try {
            mc  = Gson().fromJson(json, clazz)
        } catch (e: Exception) {
        }
        return mc
    }

}