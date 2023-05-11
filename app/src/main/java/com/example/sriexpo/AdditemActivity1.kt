package com.example.sriexpo


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sriexpo.databinding.ActivityAdditemBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdditemActivity1 : AppCompatActivity() {

    private lateinit var binding : ActivityAdditemBinding
    private lateinit var database : DatabaseReference
    private lateinit var addBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdditemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Packages")

        addBtn = binding.addBtn
        addBtn.setOnClickListener {
            val iname = binding.iname.text.toString()
            val iprice = binding.iprice.text.toString()
            val des = binding.des.text.toString()
            val uid = binding.uid.text.toString()

            val item = Item1(iname, iprice, des, uid)

            database.child(uid).setValue(item).addOnSuccessListener {
                binding.iname.text.clear()
                binding.iprice.text.clear()
                binding.des.text.clear()
                binding.uid.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, DisplayitemActivity1::class.java)
            startActivity(intent)
            finish()
        }
    }
}






