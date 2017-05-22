package org.liberty.android.uselessbot

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import org.liberty.android.uselessbot.entity.Conversation

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    val conversations: LiveData<List<Conversation>>
            get() = getApplication<MyApplication>().database.conversationDao().queryAll()
}
