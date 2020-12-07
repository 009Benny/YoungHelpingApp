package com.benny.younghelpingapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benny.younghelpingapp.R
import com.benny.younghelpingapp.adapters.MessageAdapter
import com.benny.younghelpingapp.models.YHMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private var chatId = ""
    private var user = ""
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        intent.getStringExtra("chatId")?.let { chatId = it }
        intent.getStringExtra("user")?.let { user = it }
        if(chatId.isNotEmpty() && user.isNotEmpty()) {
            initViews()
        }
    }

    fun initViews(){
        messagesRecylerView.layoutManager = LinearLayoutManager(this)
        messagesRecylerView.adapter = MessageAdapter(user)
        sendMessageButton.setOnClickListener { sendMessage() }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { messages ->
                    val listMessages = messages.toObjects(YHMessage::class.java)
                    (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
                .addSnapshotListener { messages, error ->
                    if(error == null){
                        messages?.let {
                            val listMessages = it.toObjects(YHMessage::class.java)
                            (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                        }
                    }
                }
    }

    private fun sendMessage(){
        val message = YHMessage(
                message = messageTextField.text.toString(),
                from = user
        )
        db.collection("chats").document(chatId).collection("messages").document().set(message)
        messageTextField.setText("")
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}