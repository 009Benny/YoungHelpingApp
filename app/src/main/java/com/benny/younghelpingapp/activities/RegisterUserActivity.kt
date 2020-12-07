package com.benny.younghelpingapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.benny.younghelpingapp.R
import com.benny.younghelpingapp.models.YHUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterUserActivity : AppCompatActivity() {
    private var textFieldEmail: EditText? = null
    private var textFieldPasword: EditText? = null
    private var textFieldName: EditText? = null
    private var textFieldNickname: EditText? = null
    private var textFieldSummary: EditText? = null
    private var textFieldAddress: EditText? = null
    private var textFieldCity: EditText? = null
    private var switchHelp: Switch? = null
    val db = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        this.textFieldEmail = findViewById(R.id.textFieldEmail) as EditText
        this.textFieldPasword = findViewById(R.id.textFieldPassword) as EditText
        this.textFieldName = findViewById(R.id.textFieldName) as EditText
        this.textFieldNickname = findViewById(R.id.textFieldNickName) as EditText
        this.textFieldSummary = findViewById(R.id.textFieldSummary) as EditText
        this.textFieldAddress = findViewById(R.id.textFieldAddress) as EditText
        this.textFieldCity = findViewById(R.id.textFieldCity) as EditText
        this.switchHelp = findViewById(R.id.switchHelp) as Switch
        val button = findViewById(R.id.btnRegisterUser) as Button
        button.setOnClickListener { this.btnRegisterAction() }
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun btnRegisterAction(){
        val email = this.textFieldEmail?.text.toString()
        val password = this.textFieldPasword?.text.toString()
        if (!this.textFieldEmail!!.text.isEmpty() && !this.textFieldPasword!!.text.isEmpty() && !this.textFieldName!!.text.isEmpty() && !this.textFieldNickname!!.text.isEmpty() && !this.textFieldSummary!!.text.isEmpty() && !this.textFieldAddress!!.text.isEmpty() && !this.textFieldCity!!.text.isEmpty() ) {
            if ( Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val mapita = hashMapOf("email" to email,
                                "name" to this.textFieldName?.text.toString(),
                                "nickname" to this.textFieldNickname?.text.toString(),
                                "summary" to this.textFieldSummary?.text.toString(),
                                "address" to this.textFieldAddress?.text.toString(),
                                "city" to this.textFieldCity?.text.toString().toUpperCase(),
                                "helper" to this.switchHelp?.isChecked)
                        db.collection("users").document(email).set(mapita).addOnSuccessListener { documentReference ->
                            Toast.makeText(baseContext, "Usuario creado correctamente.", Toast.LENGTH_LONG).show()
                            onBackPressed()
                        }.addOnFailureListener { e ->
                            Toast.makeText(baseContext, "No se pudieron cargar todos los datos, favor de editarlos despues.", Toast.LENGTH_LONG).show()
                            Log.w("RegisterUserActivity", "${e.message}")
                        }
                    } else {
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
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Formulario incompleto")
            builder.setMessage("Favor de llenar todos los campos, solamente el switch puede quedar invalidado")
            builder.show()
        }
    }

}