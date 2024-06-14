package com.example.emosense.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.data.response.ForumItem
import com.example.emosense.data.response.Replies
import com.example.emosense.databinding.ItemCommentBinding
import com.example.emosense.databinding.ItemForumBinding

class ListReplyAdapter: ListAdapter<Replies, ListReplyAdapter.ViewHolder>(ListReplyAdapter.DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: ListForumAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListForumAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListReplyAdapter.ViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListReplyAdapter.ViewHolder, position: Int) {
        val reply = getItem(position)
        holder.bind(reply)
    }

    class ViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        private val desc = binding.tvDesc
        private val name = binding.tvName

        fun bind(reply: Replies) {
            desc.text = reply.isi
            name.text = reply.userName
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Replies>() {
            override fun areItemsTheSame(oldItem: Replies, newItem: Replies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Replies, newItem: Replies): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}
