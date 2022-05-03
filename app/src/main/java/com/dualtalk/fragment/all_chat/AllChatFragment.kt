package com.dualtalk.fragment.all_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dualtalk.R
import com.dualtalk.databinding.FragmentAllChatBinding


class AllChatFragment : Fragment() {
    private lateinit var dataBinding: FragmentAllChatBinding
    private lateinit var viewModel: AllChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_chat, container, false)
        viewModel = ViewModelProviders.of(this)[AllChatViewModel::class.java]
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        dataBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.uiState.observe(viewLifecycleOwner) {
            if (it == AllChatViewModel.AllChatState.Update) {
                val allChatAdapter = AllChatAdapter(requireContext(), viewModel.model)
                dataBinding.recyclerView.adapter = allChatAdapter
            }
        }
    }
}