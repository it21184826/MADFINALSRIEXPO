package com.example.sriexpo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sriexpo.ExportingItems
import com.example.sriexpo.R
import com.google.android.material.imageview.ShapeableImageView

class ItemAdapter(private val itemList: ArrayList<ExportingItems>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>(), Filterable {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener){

        mListener = listener

    }

    private var filteredItemList: ArrayList<ExportingItems> = ArrayList()

    init {
        filteredItemList.addAll(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filteredItemList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
    }

    class MyViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)

        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString().trim()
                filteredItemList = if (charString.isEmpty()) {
                    itemList
                } else {
                    val filteredList: ArrayList<ExportingItems> = ArrayList()
                    for (item in itemList) {
                        if (item.heading.contains(charString, ignoreCase = true)) {
                            filteredList.add(item)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredItemList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItemList = results?.values as? ArrayList<ExportingItems> ?: ArrayList()
                notifyDataSetChanged()
            }
        }
    }
}
