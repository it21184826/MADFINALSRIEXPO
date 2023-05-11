
package com.example.sriexpo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sriexpo.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var lastName : EditText
    private lateinit var id : EditText
    private lateinit var email : EditText
    private lateinit var passET : EditText
    private lateinit var confirmPassEt : EditText
    private lateinit var registerBtn : Button
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_register)

        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        id = findViewById(R.id.id)
        email = findViewById(R.id.email)
        passET = findViewById(R.id.passET)
        confirmPassEt = findViewById(R.id.confirmPassEt)
        registerBtn = findViewById(R.id.registerBtn)


        dbRef = FirebaseDatabase.getInstance().getReference("users")
        firebaseAuth = FirebaseAuth.getInstance()

        registerBtn.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {
        val firstNameValue = firstName.text.toString().trim()
        val lastNameValue = lastName.text.toString().trim()
        val idValue = id.text.toString().trim()
        val emailValue = email.text.toString().trim()
        val passValue = passET.text.toString().trim()
        val confirmPassValue = confirmPassEt.text.toString().trim()

        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || idValue.isEmpty() || emailValue.isEmpty() || passValue.isEmpty() || confirmPassValue.isEmpty()) {
            Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_LONG).show()
        } else if (passValue.length < 6) {
            Toast.makeText(this, "Password should be at least 6 characters.", Toast.LENGTH_LONG).show()
        } else if (passValue != confirmPassValue) {
            Toast.makeText(this, "Password and Confirm Password should match.", Toast.LENGTH_LONG).show()
        } else {
            // Input validation passed, create user in Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(emailValue, passValue)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // User created successfully, save user data to Realtime Database
                        val userId = firebaseAuth.currentUser?.uid
                        val user = User(firstNameValue, lastNameValue, idValue, emailValue, passValue, confirmPassValue)

                        val usersRef = FirebaseDatabase.getInstance().getReference("users")

                        usersRef.child(userId!!).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_LONG).show()
                                firstName.text.clear()
                                lastName.text.clear()
                                id.text.clear()
                                email.text.clear()
                                passET.text.clear()
                                confirmPassEt.text.clear()

                                // Navigate to Login page
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { err ->
                                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                }
        }
    }
    }





