package org.liberty.android.uselessbot.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.liberty.android.uselessbot.entity.Conversation

@Database(entities = arrayOf(Conversation::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
}
