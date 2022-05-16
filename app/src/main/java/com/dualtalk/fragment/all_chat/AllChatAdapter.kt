package com.dualtalk.fragment.all_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.common.CurrentUser
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
            var position = 0
            if (item.participantIds[0] == CurrentUser.id && item.participantNames.size > 1) {
                position = 1
            }
            Glide.with(itemView).load(item.participantImgUrls[position])
                .into(itemView.circleImgView)
            itemView.tvName?.text = item.participantNames[position]
            if (position == 0) {
                itemView.tvMessage?.text = "You: ${item.latestMessage}"
            } else {
                itemView.tvMessage?.text = item.latestMessage
            }
            if (item.updatedAt != null) {
                itemView.tvUpdatedAt.text = DateTimeHelper.timestampToDateTimeString(item.updatedAt)
            }
        }
    }
}

interface IAllChatListener {
    fun doClickItemChat(item: ChatModel?)
}