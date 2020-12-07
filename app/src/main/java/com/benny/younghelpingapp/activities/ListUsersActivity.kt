package com.benny.younghelpingapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benny.younghelpingapp.R
import com.benny.younghelpingapp.adapters.UseritemAdapter
import com.benny.younghelpingapp.models.YHChat
import com.benny.younghelpingapp.models.YHUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ListUsersActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    var myRecycler:RecyclerView? = null
    var user:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_users)
        intent.getStringExtra("user")?.let { this.user = it }
        this.myRecycler = findViewById(R.id.usersRecycler) as RecyclerView
        this.myRecycler?.layoutManager = LinearLayoutManager(this)
//        this.myRecycler?.adapter = UseritemAdapter()
        this.myRecycler?.adapter = UseritemAdapter{ user ->
            this.showMessageOfUser(user)
        }
        if (this.user.isNotEmpty()){
            this.initViews()
        }
    }

    private fun initViews(){
        val usersRef = db.collection("users")
        usersRef.whereNotEqualTo("email", this.user).get()
            .addOnSuccessListener { users ->
//                    Toast.makeText(baseContext, "Usuarios cargados completamente", Toast.LENGTH_LONG).show()
                val list = users.toObjects(YHUser::class.java)
                (this.myRecycler?.adapter as UseritemAdapter).setData(list)
            }
        usersRef.whereNotEqualTo("email", this.user)
            .addSnapshotListener { users, error ->
                if (error == null){
                    users?.let{
                        val list = it.toObjects(YHUser::class.java)
                        (this.myRecycler?.adapter as UseritemAdapter).setData(list)
                    }
                }
        }
    }

    private fun showMessageOfUser(user: YHUser){
        db.collection("chatsUser").document(this.user).collection("chats")
            .whereEqualTo("name", "Chat con ${user.email}")
            .get()
            .addOnSuccessListener { chats ->
                if (chats.size() == 0){
                    db.collection("chatsUser").document(this.user).collection("chats")
                            .whereEqualTo("name", "Chat con ${this.user}")
                            .get()
                            .addOnSuccessListener { chatsdos ->
                                if (chatsdos.size() == 0){
                                    val chat:YHChat = this.createChat(user.email)
                                    this.showChat(chat)
                                }else{
                                    val list = chatsdos.toObjects(YHChat::class.java)
                                    val chat = list[0]
                                    this.showChat(chat)
                                }
//                                Toast.makeText(baseContext, "El segunda consulta email $this.user tiene ${chatsdos.size()}", Toast.LENGTH_LONG).show()
                            }
                }else{
                    val list = chats.toObjects(YHChat::class.java)
                    val chat = list[0]
                    this.showChat(chat)
                }
//                Toast.makeText(baseContext, "El email ${this.user} tiene ${chats.size()}", Toast.LENGTH_LONG).show()
            }
    }

    fun showChat(chat:YHChat) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chat.id)
        intent.putExtra("user", this.user)
        startActivity(intent)
        finish()
    }

    fun createChat(emailDesinty:String):YHChat{
        val chatId = UUID.randomUUID().toString()
        val chat = YHChat(
            id = chatId,
            name = "Chat con $emailDesinty",
            users = listOf(this.user, emailDesinty)
        )
        db.collection("chats").document(chatId).set(chat)
        db.collection("chatsUser").document(this.user).collection("chats").document(chatId).set(chat)
        db.collection("chatsUser").document(emailDesinty).collection("chats").document(chatId).set(chat)
        return chat
    }

}