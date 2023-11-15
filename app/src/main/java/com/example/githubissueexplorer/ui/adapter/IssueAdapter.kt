package com.example.githubissueexplorer.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.githubissueexplorer.R
import com.example.githubissueexplorer.data.model.IssueResponseItem
import com.example.githubissueexplorer.databinding.IssueListLytBinding
import java.text.SimpleDateFormat
import java.util.Locale

class IssueAdapter(private val onClickListner: OnClickListner) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {
    private var list = emptyList<IssueResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding =
            IssueListLytBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding,onClickListner)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setUpdate(list: List<IssueResponseItem>) {
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
        fun bind(issueResponseItem: IssueResponseItem) {
            binding.issueResponseItem = issueResponseItem
            binding.tvCreatedAtValue.text = formatDate(issueResponseItem.created_at, )
            Glide.with(binding.img).load(issueResponseItem.user?.avatar_url)
                .placeholder(R.drawable.ic_placeholder).into(binding.img)
            setListener(issueResponseItem)
        }

        private fun setListener(issueResponseItem: IssueResponseItem) {

            binding.root.setOnClickListener{
                onClickListener.onItemClicked(issueResponseItem.id)
            }
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
    fun onItemClicked(position: Int)
}