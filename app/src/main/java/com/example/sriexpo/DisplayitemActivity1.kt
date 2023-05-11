package com.example.sriexpo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DisplayitemActivity1 : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userArrayList : ArrayList<Ditem1>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayitem1)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)


        userArrayList = arrayListOf<Ditem1>()
        getUserData()

    }
    private fun getUserData() {

        userRecyclerview.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Packages")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val item = userSnapshot.getValue(Ditem1::class.java)
                        userArrayList.add(item!!)

                    }
                    val Adapter =  MyAdapter1(userArrayList)
                    userRecyclerview.adapter = Adapter

                    Adapter.setOnItemClickListener(object : MyAdapter1.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@DisplayitemActivity1, ItemDetailsActivity1::class.java)

                            //put extras
                            intent.putExtra("iname", userArrayList[position].iname)
                            intent.putExtra("iprice",userArrayList[position].iprice)
                            intent.putExtra("des", userArrayList[position].des)
                            intent.putExtra("uid", userArrayList[position].uid)

                            startActivity(intent)
                        }

                    })

                    userRecyclerview.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
