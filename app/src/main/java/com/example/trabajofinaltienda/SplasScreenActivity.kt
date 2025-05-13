package com.example.trabajofinaltienda

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.trabajofinaltienda.Vendedor.MainActivityVendedor

class SplasScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas_screen)

        verBienvenida()

    }


}

private  fun SplasScreenActivity.verBienvenida(){
    object : CountDownTimer(3000,1000){
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            startActivity(Intent(applicationContext, MainActivityVendedor::class.java))
            finishAffinity()
        }

    }.start()

}