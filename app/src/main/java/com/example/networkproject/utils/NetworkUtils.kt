package com.example.networkproject.utils

import android.net.Uri
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NetworkUtils {
    val VK_API_BASE_URL: String = "https://api.vk.com/"
    val VK_USERS_GET_METHOD: String = "method/users.get"
    val VK_PARAM_USER_ID: String = "user_ids"
    val VK_PARAM_FIELDS: String = "fields"
    val VK_PARAM_VERSION: String = "v"
    //Токен не воруйте пж
    val PARAM_ACCESS_TOKEN: String = "access_token"

    fun generateUrl(userId: String): URL {
        val buildUri: Uri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET_METHOD)
            .buildUpon()
            .appendQueryParameter(VK_PARAM_USER_ID, userId)
            .appendQueryParameter(VK_PARAM_FIELDS, "photo_max")
            .appendQueryParameter(VK_PARAM_VERSION, "5.89")
            .appendQueryParameter(PARAM_ACCESS_TOKEN, "8c0424e68c0424e68c0424e6ba8c7ec38f88c048c0424e6ede2d46e1932cbe3c4e37b93")
            .build()
        return URL(buildUri.toString())
    }

    fun getResponseFromUrl(url: URL): String? {
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        try {
            val enter: InputStream = urlConnection.inputStream
            val scanner: Scanner = Scanner(enter)
            scanner.useDelimiter("\\A")
            val hasInput: Boolean = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}