package com.dualtalk.fragment.all_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.Constant
import com.dualtalk.helper.DateTimeHelper
import kotlinx.android.synthetic.main.item_all_chat.view.*

class AllChatAdapter(
    private val listener: IAllChatListener?,
    private val list: ArrayList<ChatModel>
) :
    RecyclerView.Adapter<AllChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bindData(item = model)

        holder.itemView.setOnClickListener {
            listener?.doClickItemChat(item = model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bindData(item: ChatModel) {
            if (item.participantNames[0] == Constant.sendName && item.participantNames.size > 1) {
                itemView.tvName?.text = item.participantNames[1]
            } else {
                itemView.tvName?.text = item.participantNames[0]
            }
            itemView.tvMessage?.text = item.latestMessage
            itemView.tvUpdatedAt?.text = DateTimeHelper.timestampToDateTimeString(item.updatedAt!!)
        }
    }
}

interface IAllChatListener {
    fun doClickItemChat(item: ChatModel?)
}