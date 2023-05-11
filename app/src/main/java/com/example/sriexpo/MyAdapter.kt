package com.example.sriexpo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<Ditem>): RecyclerView.Adapter<MyAdapter.ViewHolder> (){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
        parent,false)

        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.iname.text = currentitem.iname

    }
    override fun getItemCount(): Int {
       return userList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val iname: TextView = itemView.findViewById(R.id.tvpName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }


        }
    }
}