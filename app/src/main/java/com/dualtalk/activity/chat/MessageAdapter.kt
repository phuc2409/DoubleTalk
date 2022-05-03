package com.dualtalk.activity.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.common.Constant
import com.dualtalk.helper.DateTimeHelper
import kotlin.collections.ArrayList

class MessageAdapter(private val list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        if (model.sendId == Constant.sendId) {
            holder.textViewSendId.gravity = Gravity.END
            holder.textViewMessage.gravity = Gravity.END
            holder.textViewCreatedAt.gravity = Gravity.END
        }

        holder.textViewSendId.text = "Send: ${model.sendId}"
        holder.textViewMessage.text = model.message

        if (model.createdAt != null) {
            holder.textViewCreatedAt.text = "Created at: ${DateTimeHelper.timestampToDateTimeString(model.createdAt)}"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //todo: dataBinding cho đoạn này
        val textViewSendId: TextView = itemView.findViewById(R.id.textViewSendId)
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
        val textViewCreatedAt: TextView = itemView.findViewById(R.id.textViewCreatedAt)
    }
}