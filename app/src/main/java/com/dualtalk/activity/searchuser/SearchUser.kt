package com.dualtalk.activity.searchuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivitySearchUserBinding

class SearchUser : AppCompatActivity() {
    private lateinit var databiding: ActivitySearchUserBinding
    private lateinit var viewModel: SearchUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databiding = DataBindingUtil.setContentView(this ,R.layout.activity_search_user)
        viewModel = ViewModelProvider(this).get(SearchUserViewModel::class.java)





    }
}