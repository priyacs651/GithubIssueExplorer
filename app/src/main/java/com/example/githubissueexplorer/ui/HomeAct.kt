package com.example.githubissueexplorer.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubissueexplorer.NetworkManager
import com.example.githubissueexplorer.R
import com.example.githubissueexplorer.databinding.HomeLytBinding
import com.example.githubissueexplorer.ui.adapter.IssueAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAct : ComponentActivity() {
    private lateinit var binding: HomeLytBinding
    private lateinit var issueViewModel: IssueViewModel
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_lyt)
        issueViewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
        initNetworkObserver()
        initAdapter()
        initObserver()
    }

    private fun initNetworkObserver() {
        val networkManager = NetworkManager(this)
        networkManager.observe(this){
            showProgress(true)
            if(it == true){
                Toast.makeText(this,"Network is available",Toast.LENGTH_SHORT).show()
                issueViewModel.getAllDataFromRetrofit()
            }else{
                Toast.makeText(this,"Network is not available loading locally available data",Toast.LENGTH_SHORT).show()
                issueViewModel.getAllDataFromDb()
            }

        }
    }


    private fun initObserver() {
        issueViewModel.issueResponseLiveData.observe(this, Observer { issueList ->
            showProgress(false)
            issueAdapter.setUpdate(issueList)
            binding.apply {
                if (issueList.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    tvDataNotFound.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    tvDataNotFound.visibility = View.GONE
                }
            }
        })

        issueViewModel.showErrorLiveData.observe(this, Observer {
            showProgress(false)
            it?.let {
                Toast.makeText(this@HomeAct, it, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeAct)
            issueAdapter = IssueAdapter(this@HomeAct)
            binding.recyclerView.adapter = issueAdapter
        }
    }

    fun showProgress(showProgress: Boolean) {
        binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
    }

}
