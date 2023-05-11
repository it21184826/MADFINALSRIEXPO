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

class DisplayitemActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userArrayList : ArrayList<Ditem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayitem)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)


        userArrayList = arrayListOf<Ditem>()
        getUserData()

    }
    private fun getUserData() {

        userRecyclerview.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Items")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){
                        val item = userSnapshot.getValue(Ditem::class.java)
                        userArrayList.add(item!!)

                    }
                    val Adapter =  MyAdapter(userArrayList)
                    userRecyclerview.adapter = Adapter

                    Adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@DisplayitemActivity, ItemDetailsActivity::class.java)

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
