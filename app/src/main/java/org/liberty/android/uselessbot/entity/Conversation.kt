package org.liberty.android.uselessbot.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.liberty.android.uselessbot.util.toIsoString
import java.util.*

@Entity(tableName = "conversations")
class Conversation constructor() {

    constructor(text: String): this() {

        id = UUID.randomUUID().toString()
        incoming = false
        content = text
        date = Date().toIsoString()
    }


    @PrimaryKey
    var id: String = ""

    @ColumnInfo(name = "incoming")
    var incoming: Boolean = false

    @ColumnInfo(name = "content")
    var content: String = ""

    @ColumnInfo(name = "date")
    var date: String = ""
}
