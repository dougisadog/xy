package corelib.util

import corelib.extension.joined
import java.net.URLEncoder

/**
 *
 * Created by Yamamoto Keita on 2018/01/17.
 */
object QueryUtils {

    fun makeQueryString(items: List<Pair<String, Any?>>, encoder: ((String) -> (String))? = null) : String? {
        if (items.isNotEmpty()) {

            val queries = items.map {
                if (encoder != null) {
                    encoder(it.first) + "=" + encoder(it.second.toString() ?: "")
                } else {
                    urlEncode(it.first) + "=" + urlEncode(
                        it.second.toString()
                            ?: ""
                    )
                }
            }

            return queries.joined(separator = "&")
        }
        return null
    }

    fun urlEncode(str: String) : String {
        return URLEncoder.encode(str, "UTF-8")
    }
}
