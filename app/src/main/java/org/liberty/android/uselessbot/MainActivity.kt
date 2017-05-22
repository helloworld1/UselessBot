package org.liberty.android.uselessbot

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.liberty.android.uselessbot.entity.Conversation
import org.liberty.android.uselessbot.model.QueryResponse
import org.liberty.android.uselessbot.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class MainActivity : LifecycleActivity() {
    var viewModel by Delegates.notNull<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById(R.id.input) as EditText
        val sendButton = findViewById(R.id.send) as Button

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ConversationAdapter()
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.conversations.observe(this, Observer<List<Conversation>> {
            adapter.setItems(it ?: Collections.emptyList())

            if (it != null) {
                recyclerView.layoutManager.scrollToPosition(it.size - 1)
            }
        })

        this.database.conversationDao().queryAll()

        sendButton.setOnClickListener {
            sendMessage(input.text.toString())
            input.setText("")
        }
    }

    private fun sendMessage(text: String) {
        executorService.submit({
            val conversation = Conversation(text)
            database.conversationDao().insertConversation(conversation)
        })

        apiAiService.query(text, defaultLang, sessionId).enqueue(object : Callback<QueryResponse> {
            override fun onResponse(call: Call<QueryResponse>?, response: Response<QueryResponse>?) {
                // Elvis operator
                response ?: return

                if (response.isSuccessful) {
                    println("Body timestamp: ${response.body()?.timestamp}")
                    println("Body id: ${response.body()?.id}")
                    println("Body query: ${response.body()?.result?.resolvedQuery}")
                    println("Body speech: ${response.body()?.result?.speech}")
                    executorService.submit({
                        val conversation = Conversation(response.body()?.result?.speech ?: "N/A")
                        conversation.incoming = true
                        database.conversationDao().insertConversation(conversation)
                    })
                }
            }

            override fun onFailure(call: Call<QueryResponse>?, t: Throwable?) {
                if (t != null) {
                    throw t
                }
            }

        })

    }

}
