package com.example.sriexpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var cubtn : Button
    private lateinit var Sebtn : Button
    private lateinit var clbtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        cubtn = findViewById(R.id.cubtn)
        Sebtn = findViewById(R.id.Sebtn)
        clbtn = findViewById(R.id.clbtn)

        cubtn.setOnClickListener {
            val intent = Intent(this, MainActivity1::class.java)
            startActivity(intent)
        }

        Sebtn.setOnClickListener {
            val intent = Intent(this, SellarActivity::class.java)
            startActivity(intent)
            finish()

        }

        clbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}