package com.demosample.smsanalyze

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demosample.smsanalyze.MessageAdapter.MyViewHolder
import java.util.*

class MessageAdapter(var context: Context, var msdList: ArrayList<String>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.recycler_list
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.messageList.text = msdList[position]
    }

    override fun getItemCount(): Int {
        return msdList.size
    }

    fun notifyChange(newList: ArrayList<String>) {
        msdList = newList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var messageList: TextView

        init {
            messageList = itemView.findViewById(R.id.messageDisplayer)
        }
    }

}