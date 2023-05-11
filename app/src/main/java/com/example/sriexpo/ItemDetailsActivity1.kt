package com.example.sriexpo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ItemDetailsActivity1 : AppCompatActivity() {

    private lateinit var tvpname: TextView
    private lateinit var tvprice: TextView
    private lateinit var tvdes: TextView
    private lateinit var tvpid: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details1)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("uid").toString(),
                intent.getStringExtra("iname").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("uid").toString()
            )
        }
    }




    private fun setValuesToViews() {

        tvpname.text = intent.getStringExtra("iname")
        tvprice.text = intent.getStringExtra("iprice")
        tvdes.text = intent.getStringExtra("des")
        tvpid.text = intent.getStringExtra("uid")

    }

    private fun initView() {
        tvpname = findViewById(R.id.tvpname)
        tvprice = findViewById(R.id.tvprice)
        tvdes = findViewById(R.id.tvdes)
        tvpid = findViewById(R.id.tvpid)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun deleteRecord(uid: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Items").child(uid)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Item data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, DisplayitemActivity1::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(uid: String, iname: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog1, null)

        mDialog.setView(mDialogView)

        val etiName = mDialogView.findViewById<EditText>(R.id.etiName)
        val etiprice = mDialogView.findViewById<EditText>(R.id.etiprice)
        val etdes = mDialogView.findViewById<EditText>(R.id.etdes)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etiName.setText(intent.getStringExtra("iname").toString())
        etiprice.setText(intent.getStringExtra("iprice").toString())
        etdes.setText(intent.getStringExtra("des").toString())

        mDialog.setTitle("Updating $iname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateItemData(
                uid,
                etiName.text.toString(),
                etiprice.text.toString(),
                etdes.text.toString()
            )

            Toast.makeText(applicationContext, "Item Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvpname.text = etiName.text.toString()
            tvprice.text = etiprice.text.toString()
            tvdes.text = etdes.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateItemData(
        id: String,
        name: String,
        price: String,
        des: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Items").child(id)
        val itemInfo = Ditem1(name, price, des, id)
        dbRef.setValue(itemInfo)
    }

}

