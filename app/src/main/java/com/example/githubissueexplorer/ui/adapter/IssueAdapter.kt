package com.example.githubissueexplorer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.githubissueexplorer.R
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
import com.example.githubissueexplorer.databinding.IssueListLytBinding
import org.mongodb.kbson.ObjectId
import java.text.SimpleDateFormat
import java.util.Locale

class IssueAdapter(private val onClickListner: OnClickListner) :
  RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {
  private var list = emptyList<IssueResponseItemModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
    val binding =
      IssueListLytBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return IssueViewHolder(binding, onClickListner)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun setUpdate(list: List<IssueResponseItemModel>) {
    this.list = list
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
    holder.bind(list.get(position))
  }

  class IssueViewHolder(
    val binding: IssueListLytBinding,
    val onClickListener: OnClickListner
  ) : ViewHolder(binding.root) {
    fun bind(issueResponseItemModel: IssueResponseItemModel) {
      binding.issueResponseItemModel = issueResponseItemModel
      binding.tvCreatedAtValue.text = formatDate(issueResponseItemModel.createdAt)
      Glide.with(binding.img).load(issueResponseItemModel.user?.avatarUrl)
        .placeholder(R.drawable.ic_placeholder).into(binding.img)

      setListener(issueResponseItemModel)
    }

    private fun setListener(issueResponseItem: IssueResponseItemModel) {

      binding.root.setOnClickListener {
        onClickListener.onItemClicked(issueResponseItem.id)
      }

      binding.root.setOnLongClickListener(object : OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
          onClickListener.onItemLongClick(issueResponseItem.id, issueResponseItem)
          return true
        }
      })
    }

    private fun formatDate(originalDate: String): String {
      try {
        val inputDateFormat =
          SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

        val date = inputDateFormat.parse(originalDate)
        if (date != null) {
          return outputDateFormat.format(date)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }

      return originalDate
    }
  }
}

interface OnClickListner {
  fun onItemClicked(id: Int)
  fun onItemLongClick(id: Int, issueResponseItem: IssueResponseItemModel)
}