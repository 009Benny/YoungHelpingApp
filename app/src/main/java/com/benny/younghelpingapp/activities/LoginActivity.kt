package com.benny.younghelpingapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.benny.younghelpingapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val auth = Firebase.auth
    private var textFieldEmail: EditText? = null
    private var textFieldPasword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonLoggin = findViewById(R.id.btnLogin) as Button
        val buttonRegister = findViewById(R.id.btnRegister) as Button
        this.textFieldEmail = findViewById(R.id.textFieldEmail) as EditText
        this.textFieldPasword = findViewById(R.id.textFieldPassword) as EditText
        buttonLoggin.setOnClickListener { loginUser() }
        buttonRegister.setOnClickListener { showRegisterUser() }

        checkUser()
    }


    private fun checkUser(){
        val currentUser = auth.currentUser
        if(currentUser != null){
//            val intent = Intent(this, ListUsers::class.java)
//            intent.putExtra("user", currentUser.email)
//            startActivity(intent)
//            finish()
        }
    }

    private fun createUser(){
//        val email = emailText.text.toString()
//        val password = passwordText.text.toString()
//
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                checkUser()
//            } else {
//                task.exception?.let {
//                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//
//        }
    }

    private fun showRegisterUser(){
        val intent = Intent(this, RegisterUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginUser(){
        val email:String = this.textFieldEmail?.text.toString()
        val password = this.textFieldPasword?.text.toString()
        if (email != null && password != null) {
            if ( Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        checkUser()
                    }else{
                        task.exception?.let {
                            Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Correo invalido")
                builder.setMessage("Favor de ingresar un correo valido")
                builder.show()
            }
        }
    }

}