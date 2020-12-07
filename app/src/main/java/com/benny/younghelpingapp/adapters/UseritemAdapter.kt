package com.benny.younghelpingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benny.younghelpingapp.R
import com.benny.younghelpingapp.models.YHUser
import kotlinx.android.synthetic.main.user_item.view.*

class UseritemAdapter(val chatClick: (YHUser) -> Unit): RecyclerView.Adapter<UseritemAdapter.UseritemViewHolder>() {

    class UseritemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    var listOfUser:List<YHUser> = emptyList()

    fun setData(list: List<YHUser>){
        this.listOfUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseritemViewHolder {
        return UseritemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UseritemViewHolder, position: Int) {
        var user = listOfUser[position]
        holder.itemView.lblNameItemUser.text = user.name.toString()
        holder.itemView.lblRsumenItemUser.text = user.summary.toString()
        if (user.helper){
            holder.itemView.imgBullet.setImageResource(R.drawable.red_circle)
        }else{
            holder.itemView.imgBullet.setImageResource(R.drawable.blue_circle)
        }
        holder.itemView.btnMessageItemUser.setOnClickListener {
            chatClick(user)
        }
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }
}