package corelib.convert

interface Parser {

//     enum class ParsType {
//         XML,
//         JSON
//         ;
//     }
//
//     var type:ParsType

    fun <T> parse(src:String, clazz: Class<T>):T?

    fun <T> parseList(src:String, clazz: Class<T>):List<T>?
 }