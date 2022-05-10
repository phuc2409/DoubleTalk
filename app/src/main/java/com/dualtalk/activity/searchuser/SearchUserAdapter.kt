package com.dualtalk.activity.searchuser

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dualtalk.R
import de.hdodenhof.circleimageview.CircleImageView

class SearchUserAdapter(context: Context,list: List<MUser>) : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() , Filterable {
    private var mlist: List<MUser>
    private var mlistold: List<MUser>
    var mcontext : Context

    init {
        mlist = list
        mlistold = mlist
        mcontext = context
    }


    class SearchUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
         var imgUser : CircleImageView
         var txtViewNameUser : TextView
         var layoutItem : RelativeLayout

        init {
            imgUser = itemView.findViewById(R.id.search_imgUser)
            txtViewNameUser = itemView.findViewById(R.id.Search_tennguoidung)
            layoutItem = itemView.findViewById(R.id.search_user_layout_item)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return SearchUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        val user: MUser = mlist[position] ?: return

        //set ảnh đại diện cho các user
        Glide.with(holder.itemView).load(user.UrlImageUser).into(holder.imgUser)
        holder.txtViewNameUser.text = user.mUsername.trim()
        holder.layoutItem.setOnClickListener {
            onClickGotoDetail(user)
        }
    }

    private fun onClickGotoDetail(user:MUser){
        Toast.makeText(this.mcontext,"Đang vào giao diện nói chuyện vs ${user.mUsername}" , Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun getFilter(): Filter {
        val customFilter = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchKeyword : String = p0.toString()
                if(searchKeyword.isEmpty()){
                    mlist = mlistold
                }
                else{
                     val listtg : ArrayList<MUser> = ArrayList()
                    for(user : MUser in mlistold){
                        if(user.mUsername.lowercase().contains(searchKeyword.lowercase())){
                            listtg.add(user)
                        }
                    }
                    mlist = listtg.toList()
                }
                val filterResult = FilterResults()
                filterResult.values = mlist
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mlist = p1?.values as List<MUser>
                notifyDataSetChanged()
            }

        }
        return customFilter
    }


}