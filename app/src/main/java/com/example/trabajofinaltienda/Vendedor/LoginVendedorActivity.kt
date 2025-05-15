package com.example.trabajofinaltienda.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabajofinaltienda.databinding.ActivityLoginVendedorBinding
import com.example.trabajofinaltienda.databinding.ActivityMainVendedorBinding
import com.google.firebase.auth.FirebaseAuth

class LoginVendedorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnLoginV.setOnClickListener{
            validarInfo()
        }

        binding.tvRegistrarV.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroVendedorActivity::class.java))

        }

    }

    private var email = ""
    private var password = ""
    private fun validarInfo() {
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()

        if (email.isEmpty()){
            binding.etEmailV.error = "Ingrese email"
            binding.etEmailV.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailV.error = "Email no valido"
            binding.etEmailV.requestFocus()
        }else if (password.isEmpty()){
            binding.etEmailV.error = "Ingrese password"
            binding.etEmailV.requestFocus()
        }else{
            loginVendedor()
        }
    }

    private fun loginVendedor() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido ",
                    Toast.LENGTH_SHORT
                ).show()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesion devido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}