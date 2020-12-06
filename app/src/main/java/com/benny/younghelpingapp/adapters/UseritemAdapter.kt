package com.benny.younghelpingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benny.younghelpingapp.R

class UseritemAdapter: RecyclerView.Adapter<UseritemAdapter.UseritemViewHolder>() {

    class UseritemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    var listOfUser:List<String> = emptyList()

    fun setListOfNames(list: List<String>){
        listOfUser = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseritemViewHolder {
        return UseritemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UseritemViewHolder, position: Int) {
        var name = listOfUser[position]
//        holder.itemView
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }
}