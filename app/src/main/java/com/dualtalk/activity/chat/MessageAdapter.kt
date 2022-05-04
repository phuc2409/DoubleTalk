package com.dualtalk.activity.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.common.Constant
import com.dualtalk.helper.DateTimeHelper
import kotlinx.android.synthetic.main.item_message.view.*
import kotlin.collections.ArrayList

class MessageAdapter(private val list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bindData(model)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bindData(item: MessageModel) {
            if (item.sendId == Constant.sendId) {
                itemView.tvSendId.gravity = Gravity.END
                itemView.tvMessage.gravity = Gravity.END
                itemView.tvCreatedAt.gravity = Gravity.END
            }

            itemView.tvSendId.text = "Send: ${item.sendId}"
            itemView.tvMessage.text = item.message
            itemView.tvCreatedAt.text =
                "Created at: ${DateTimeHelper.timestampToDateTimeString(item.createdAt!!)}"
        }
    }
}