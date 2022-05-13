package com.dualtalk.fragment.all_chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.dualtalk.R
import com.dualtalk.activity.chat.ChatActivity
import com.dualtalk.activity.chat.ChatModel
import com.dualtalk.activity.searchuser.SearchUserActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_all_chat.*

class AllChatFragment : Fragment(), IAllChatListener {
    private lateinit var viewModel: AllChatViewModel
    private var allChatAdapter: AllChatAdapter? = null
    private lateinit var searchBar: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this)[AllChatViewModel::class.java]
        return inflater.inflate(R.layout.fragment_all_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        clickSearchBar(view)
    }

    private fun setupView() {
        allChatAdapter = AllChatAdapter(this, viewModel.model)

        viewModel.uiState.observe(viewLifecycleOwner) {
            if (it == AllChatViewModel.AllChatState.Update) {
                allChatAdapter = AllChatAdapter(this, viewModel.model)
                recyclerView.adapter = allChatAdapter
            }
        }
    }

    override fun doClickItemChat(item: ChatModel?) {
        val intent = Intent(context, ChatActivity::class.java)
        val gson = Gson()
        val json: String = gson.toJson(item)
        intent.putExtra("json", json)
        startActivity(intent)
    }

    private fun clickSearchBar(view: View){
        searchBar = view.findViewById(R.id.txtSearch)
        searchBar.setOnClickListener {
            val intent = Intent(view.context ,SearchUserActivity::class.java)
            startActivity(intent)
        }
    }
}