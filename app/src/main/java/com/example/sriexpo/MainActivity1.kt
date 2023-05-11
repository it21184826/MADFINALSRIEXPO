package com.example.sriexpo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {

    private lateinit var joinBtn : Button
    private lateinit var logBtn : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main1)

        joinBtn = findViewById(R.id.joinBtn)
        logBtn = findViewById(R.id.logBtn)

        logBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity1::class.java)
            startActivity(intent)
        }

        joinBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity1::class.java)
            startActivity(intent)
            finish()
        }

        }

    }


