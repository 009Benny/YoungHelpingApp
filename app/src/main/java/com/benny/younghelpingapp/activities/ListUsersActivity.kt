package com.benny.younghelpingapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benny.younghelpingapp.R
import com.benny.younghelpingapp.adapters.UseritemAdapter
import com.benny.younghelpingapp.models.YHUser
import com.google.firebase.firestore.FirebaseFirestore

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
        Toast.makeText(baseContext, "Enviar mensaje a: ${user.email}", Toast.LENGTH_LONG).show()
    }

}