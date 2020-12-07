package com.benny.younghelpingapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
        val buttonInfo = findViewById(R.id.btnInfo) as Button
        this.textFieldEmail = findViewById(R.id.textFieldEmail) as EditText
        this.textFieldPasword = findViewById(R.id.textFieldPassword) as EditText
        buttonLoggin.setOnClickListener { loginUser() }
        buttonRegister.setOnClickListener { showRegisterUser() }
        buttonInfo.setOnClickListener { btnInfo() }
        checkUser()
    }


    private fun checkUser(){
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, ListUsersActivity::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)
            finish()
        }
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
                        Toast.makeText(baseContext, "Usuario Loggeado!", Toast.LENGTH_LONG).show()
                        this.checkUser()
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

    private fun btnInfo(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
        finish()
    }

}