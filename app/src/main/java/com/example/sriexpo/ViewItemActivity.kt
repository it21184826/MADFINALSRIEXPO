package com.example.sriexpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sriexpo.ExportingItems
import com.example.sriexpo.R

class ViewItemActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ExportingItems>
    private lateinit var imageId: Array<Int>
    private lateinit var heading: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        imageId = arrayOf(
            R.drawable.coconuts,
            R.drawable.cinnamon,
            R.drawable.gems,
            R.drawable.tea,
            R.drawable.spices,
            R.drawable.apparel,
            R.drawable.ceramic,
            R.drawable.fiber
        )

        heading = arrayOf(
            "COCONUT : $5.80 ",
            "CINNAMON : $10",
            "GEMS : $1000",
            "TEA : $3",
            "SPICES : $6",
            "APPAREL : $100",
            "CERAMIC : $50",
            "FIBER : $25"
        )

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<ExportingItems>()
        getUserData()
    }

    private fun getUserData() {
        for (i in imageId.indices) {
            val exportingItems = ExportingItems(imageId[i], heading[i])
            newArrayList.add(exportingItems)
        }

        var adapter = ItemAdapter(newArrayList)
        newRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object :ItemAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

                Toast.makeText(this@ViewItemActivity, "You clicked On Item no. $position", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
