package org.liberty.android.uselessbot

import android.app.Application
import android.arch.persistence.room.Room
import org.liberty.android.uselessbot.dao.AppDatabase
import kotlin.properties.Delegates

class MyApplication : Application() {

    var database by Delegates.notNull<AppDatabase>()

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "messages")
                .build()
    }
}
