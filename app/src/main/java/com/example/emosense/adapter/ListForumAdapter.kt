package com.example.emosense.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.data.response.ForumItem
import com.example.emosense.databinding.ItemForumBinding
import com.example.emosense.utils.formatCreatedAt

class ListForumAdapter : ListAdapter<ForumItem, ListForumAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forum = getItem(position)
        holder.bind(forum)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(forum.id!!)
        }
    }

    class ViewHolder(private val binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.tvTitle
        private val desc = binding.tvDesc
        private val name = binding.tvName
        private val time = binding.tvTime
        private val numReplies = binding.tvNumofComments

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(forum: ForumItem) {
            title.text = forum.judul
            desc.text = forum.isi
            name.text = forum.userName
            time.text = formatCreatedAt(forum.createdAt ?: "")

            forum.numOfReplies?.let {
                numReplies.text = "$it"
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForumItem>() {
            override fun areItemsTheSame(oldItem: ForumItem, newItem: ForumItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ForumItem, newItem: ForumItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}
