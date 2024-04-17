package com.example.reservationproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reservationproject.R

class LogRegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_reg)
        
        supportActionBar?.hide()
    }
}