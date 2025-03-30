package com.example.mentalhealthaibot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // RecyclerView ko find karo
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChat)

        // LayoutManager set karo
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample messages list banaiye
        val messages = listOf(
            ChatMessage("Let's work on your workout plan for today. How are you feeling?", R.drawable.ic_profile),
            ChatMessage("I'm feeling great today, ready for a challenge!", R.drawable.ic_profile)
        )

        // Adapter ko set karo
        val adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter
    }
}
