package com.dualtalk.activity.searchuser

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dualtalk.R
import com.dualtalk.databinding.ActivitySearchUserBinding

class SearchUserActivity : AppCompatActivity() {
    private lateinit var databiding: ActivitySearchUserBinding
    private lateinit var viewModel: SearchUserViewModel
    private lateinit var rcvUser: RecyclerView
    private lateinit var searchUserAdapter: SearchUserAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databiding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        viewModel = ViewModelProvider(this).get(SearchUserViewModel::class.java)
        //add toolbar
        val toolbar = findViewById<Toolbar>(R.id.search_user_toolbar)
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(this)
        rcvUser = findViewById(R.id.rcv_searchUser)
        rcvUser.layoutManager = linearLayoutManager
        viewModel.getListUser()

        //gạch dưới chân mỗi User
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rcvUser.addItemDecoration(itemDecoration)
        ////////////////////////

        viewModel.uiState.observe(this) {
            if (it == SearchUserViewModel.SearchUserState.Success) {
                searchUserAdapter = SearchUserAdapter(this, viewModel.mlist)
                rcvUser.adapter = searchUserAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_user_menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.actionsearch)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUserAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchUserAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
}