package com.example.trabajofinaltienda.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabajofinaltienda.Constantes
import com.example.trabajofinaltienda.R
import com.example.trabajofinaltienda.databinding.ActivityMainVendedorBinding
import com.example.trabajofinaltienda.databinding.ActivityRegistroVendedorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere porfavor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarV.setOnClickListener{
            validarInformacion()
        }

    }
    private var nombres = ""
    private var email = ""
    private var password = ""
    private var cpassword = ""
    private fun validarInformacion() {

        nombres = binding.etNombresV.text.toString().trim()
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()
        cpassword = binding.etCPasswordV.text.toString().trim()

        if (nombres.isEmpty()){
            binding.etNombresV.error = "Ingresa un nombre"
            binding.etNombresV.requestFocus()
        }else if (email.isEmpty()){
            binding.etEmailV.error = "Ingresa email"
            binding.etEmailV.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailV.error = "Email incorrecto"
            binding.etEmailV.requestFocus()
        }else if (password.isEmpty()){
            binding.etPasswordV.error = "Ingresa contraseña"
            binding.etPasswordV.requestFocus()
        }else if (password.length <=6){
            binding.etPasswordV.error = "Necesita 6 o mas caracteres"
            binding.etPasswordV.requestFocus()
        }else if (cpassword.isEmpty()){
            binding.etCPasswordV.error = "confirme contraseña"
            binding.etCPasswordV.requestFocus()
        }else if (password!=cpassword){
            binding.etEmailV.error = "contraseñas no coinciden"
            binding.etEmailV.requestFocus()
        }else{
            registrarVendedor()
        }

    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener { e->
                Toast.makeText(this, "Fallo el registro debido a ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertarInfoBD(){
        progressDialog.setMessage("Guardando Informacion...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val emailBD = email
        val tiempoBD =  Constantes().obtenerTiempoD()

        val datosVendedor = HashMap<String, Any>()

        datosVendedor["uid"] = "$uidBD"
        datosVendedor["nombre"] = "$nombreBD"
        datosVendedor["email"] = "$emailBD"
        datosVendedor["tipoUsuario"] = "vendedor"
        datosVendedor["tiempo_registro"] = tiempoBD

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener{
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java ))
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "Fallo el registro en BD debido a ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }
}