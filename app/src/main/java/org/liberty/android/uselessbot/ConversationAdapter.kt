package org.liberty.android.uselessbot

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.liberty.android.uselessbot.entity.Conversation

class ConversationAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val conversations: MutableList<Conversation> = ArrayList()

    override fun getItemCount(): Int = conversations.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        val conversation = conversations.get(position)

        when (viewHolder) {
            is MessageViewHolder -> viewHolder.setContent(conversation.content)
            else -> TODO("Not implemented")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (conversations[position].incoming) R.layout.incoming_message_item else R.layout.outgoing_message_item;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)

        return MessageViewHolder(v)
    }

    fun appendItems(conversationList: List<Conversation>) {
        val start = itemCount

        conversations += conversationList
        notifyItemRangeInserted(start, conversationList.size)
    }

    fun setItems(newConversations: List<Conversation>) {
        val diffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun areItemsTheSame(p0: Int, p1: Int): Boolean =
                    conversations[p0].id == newConversations[p1].id

            override fun getOldListSize(): Int = conversations.size

            override fun getNewListSize(): Int = newConversations.size

            override fun areContentsTheSame(p0: Int, p1: Int): Boolean =
                    conversations[p0] == newConversations[p1]

        })

        conversations.clear()
        conversations.addAll(newConversations)
        diffResult.dispatchUpdatesTo(this)
    }


}

/**
 * ??? Android data binding doesn't work
 */
class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setContent(text: String) {
        (itemView.findViewById(R.id.content) as TextView).text = text
    }
}
