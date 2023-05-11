package com.example.sriexpo

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var add: Button
    private lateinit var view: Button
    private lateinit var tvfirstName: MaterialTextView
    private lateinit var tvlastName: MaterialTextView
    private lateinit var tvid: MaterialTextView
    private lateinit var tvuserName: MaterialTextView
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        add = findViewById(R.id.add)
        view = findViewById(R.id.view)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        tvfirstName = findViewById(R.id.tvfirstName)
        tvlastName = findViewById(R.id.tvlastName)
        tvid = findViewById(R.id.tvid)
        tvuserName = findViewById(R.id.tvuserName)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("users")

        val currentUser = auth.currentUser
        if (currentUser != null) {
            dbRef.child(currentUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user: User? = dataSnapshot.getValue(User::class.java)

                        if (user != null) {
                            tvfirstName.text = user.firstName
                            tvlastName.text = user.lastName
                            tvid.text = user.id
                            tvuserName.text = user.email
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error
                    }
                })
        }

        add.setOnClickListener {
            val intent = Intent(this, AdditemActivity::class.java)
            startActivity(intent)
            finish()
        }

        view.setOnClickListener {
            val intent = Intent(this, DisplayitemActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}