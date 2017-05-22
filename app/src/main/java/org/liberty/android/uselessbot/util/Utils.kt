package org.liberty.android.uselessbot.util

import android.app.Activity
import org.liberty.android.uselessbot.ApiAiService
import org.liberty.android.uselessbot.MyApplication
import org.liberty.android.uselessbot.dao.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

val sessionId = BigInteger(100, SecureRandom()).toString(32)

val defaultLang = "en"

val executorService = Executors.newFixedThreadPool(4)


val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.api.ai/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

val apiAiService = retrofit.create(ApiAiService::class.java)

val Activity.database: AppDatabase
    get() = (application as MyApplication).database

fun Date.toIsoString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    formatter.timeZone = TimeZone.getTimeZone("UTC");
    return formatter.format(this)
}

