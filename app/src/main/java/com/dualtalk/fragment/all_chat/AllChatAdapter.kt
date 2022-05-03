package com.dualtalk.fragment.all_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.Constant
import java.text.SimpleDateFormat

class AllChatAdapter(private val list: ArrayList<ChatModel>) :
    RecyclerView.Adapter<AllChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        if (model.participantNames[0] == Constant.sendId && model.participantNames.size > 1) {
            holder.textViewName.text = model.participantNames[1]
        } else {
            holder.textViewMessage.text = model.participantNames[0]
        }

        holder.textViewMessage.text = model.latestMessage

        if (model.updatedAt != null) {
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val netDate = model.updatedAt.toDate()
            holder.textViewUpdatedAt.text = "Created at: ${sdf.format(netDate)}"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //todo: dataBinding cho đoạn này
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
        val textViewUpdatedAt: TextView = itemView.findViewById(R.id.textViewUpdatedAt)
    }
}