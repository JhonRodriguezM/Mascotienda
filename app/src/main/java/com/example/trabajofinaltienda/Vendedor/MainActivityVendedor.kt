package com.example.trabajofinaltienda.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.trabajofinaltienda.R
import com.example.trabajofinaltienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.example.trabajofinaltienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.example.trabajofinaltienda.Vendedor.Nav_Fragments_Vendedor.FragmentInicioV
import com.example.trabajofinaltienda.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.example.trabajofinaltienda.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import com.example.trabajofinaltienda.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivityVendedor : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainVendedorBinding
    private var FirebaseAuth : FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar =  findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        FirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
        comprobarSesion()

        binding.navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer

        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioV())

        binding.navigationView.setCheckedItem(R.id.op_inicio_v)

    }

    private fun cerrarSesion(){
        FirebaseAuth!!.signOut()
        startActivity(Intent(applicationContext, LoginVendedorActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "Has cerrado sesion", Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
        /*Si el usuario no ah iniciado sesion lo redirecciona al inicio sesion*/
        if (FirebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext, LoginVendedorActivity :: class.java))
            Toast.makeText(applicationContext, "Vendedor no registrado o no se ah logueado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, "Vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.op_inicio_v->{
                replaceFragment(FragmentInicioV())
            }
            R.id.op_mi_mascotienda_v->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v->{
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v->{
                cerrarSesion()
            }
            R.id.op_mis_productos_v->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v->{
                replaceFragment(FragmentOrdenesV())
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }
}