package test.dev.importantpeople.utils

import com.google.gson.Gson

fun <T> ClassLoader.transformTo(jsonName: String, clazz: Class<T>): T {
    return Gson().fromJson(getResourceAsStream(jsonName)?.bufferedReader().use { it?.readText() }, clazz)
}