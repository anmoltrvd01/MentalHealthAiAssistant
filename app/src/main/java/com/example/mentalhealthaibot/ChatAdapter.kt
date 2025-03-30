package com.example.mentalhealthaibot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    // ViewHolder class mein tumhara custom layout elements bind hote hain
    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvCoachMessage)
        val ivAvatar: CircleImageView = view.findViewById(R.id.ivCoachAvatar)
    }

    // onCreateViewHolder mein tum custom layout inflate karte ho
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_chatmessage_item_layout, parent, false)
        return ChatViewHolder(view)
    }

    // onBindViewHolder mein data ko set karte ho
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.tvMessage.text = message.message
        holder.ivAvatar.setImageResource(message.avatar)
    }

    // getItemCount method se list ki size return hoti hai
    override fun getItemCount(): Int = messages.size
}
