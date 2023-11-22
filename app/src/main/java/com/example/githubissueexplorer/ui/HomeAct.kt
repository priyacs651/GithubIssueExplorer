package com.example.githubissueexplorer.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubissueexplorer.NetworkManager
import com.example.githubissueexplorer.R
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
import com.example.githubissueexplorer.databinding.HomeLytBinding
import com.example.githubissueexplorer.databinding.IssueListLytBinding
import com.example.githubissueexplorer.databinding.UpdateIssueLayoutBinding
import com.example.githubissueexplorer.ui.adapter.IssueAdapter
import com.example.githubissueexplorer.ui.adapter.OnClickListner
import dagger.hilt.android.AndroidEntryPoint
import org.mongodb.kbson.ObjectId

@AndroidEntryPoint
class HomeAct : ComponentActivity(), OnClickListner {
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
    networkManager.observe(this) {
      showProgress(true)
      if (it == true) {
        Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show()
        issueViewModel.getAllDataFromRemote()
      } else {
        Toast.makeText(
          this,
          "Network is not available loading locally available data",
          Toast.LENGTH_SHORT
        ).show()
        issueViewModel.getAllDataFromLocal()
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

  override fun onItemClicked(id: Int) {
    deleteAlertDialog(id)
  }

  override fun onItemLongClick(id: Int, issueResponseItemModel: IssueResponseItemModel) {
    updateAlertDialog(id, issueResponseItemModel)
  }

  private fun deleteAlertDialog(id: Int) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Delete")
    builder.setMessage("Do you want to delete this item?")
    builder.setPositiveButton("Yes") { dialog, which -> issueViewModel.deleteIssueFromLocal(id) }
    builder.setNegativeButton("No") { dialog, which -> dialog.cancel() }
    builder.create().show()
  }

  private fun updateAlertDialog(id: Int, issueResponseItemModel: IssueResponseItemModel) {
    val updateIssueListLytBinding = UpdateIssueLayoutBinding.inflate(LayoutInflater.from(this))

    updateIssueListLytBinding.etName.setText(issueResponseItemModel.user?.login)
    updateIssueListLytBinding.etTitle.setText(issueResponseItemModel.title)

    val builder = AlertDialog.Builder(this)
    builder.setView(updateIssueListLytBinding.root)
    builder.setPositiveButton("update") { dialog, which ->
      issueViewModel.updateIssueFromLocal(
        id,
        updateIssueListLytBinding.etName.text.toString(),
        updateIssueListLytBinding.etTitle.text.toString()
      )
    }
    builder.setNegativeButton("cancel") { dialog, which ->
      dialog.cancel()
    }
    builder.create().show()
  }
}
