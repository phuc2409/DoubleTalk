package com.dualtalk.activity.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.common.CurrentUser
import com.dualtalk.helper.DateTimeHelper
import kotlinx.android.synthetic.main.item_message_send.view.*
import kotlin.collections.ArrayList

class MessageAdapter(private val list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_send, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_receive, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bindData(model)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].sendId == CurrentUser.id) {
            0
        } else {
            1
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bindData(item: MessageModel) {
            itemView.tvMessage.text = item.message
            if (item.createdAt != null) {
                itemView.tvCreatedAt.text = DateTimeHelper.timestampToDateTimeString(item.createdAt)
            }
        }
    }
}