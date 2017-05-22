package org.liberty.android.uselessbot.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.liberty.android.uselessbot.entity.Conversation

@Dao
interface ConversationDao {
    @Query("select * from conversations order by date")
    fun queryAll(): LiveData<List<Conversation>>

    @Query("select id from conversations order by date")
    fun queryAllMap(): data

    /**
     * What???? arg0 instead of dateString in Kotlin???
     */
    @Query("select * from conversations where date > :arg0 order by date")
    fun queryByDate(dateString: String): LiveData<List<Conversation>>

    @Insert
    fun insertConversation(vararg conversations: Conversation)
}

data class data(val aaa)

class data {
    var abc: String = ""
    var id: String =""
}
