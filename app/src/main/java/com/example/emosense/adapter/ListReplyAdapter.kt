package com.example.emosense.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.data.response.Replies
import com.example.emosense.databinding.ItemCommentBinding
import com.example.emosense.utils.formatCreatedAt

class ListReplyAdapter : ListAdapter<Replies, ListReplyAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClickCallback)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reply = getItem(position)
        holder.bind(reply)
    }

    class ViewHolder(private val binding: ItemCommentBinding, private val clickListener: OnItemClickCallback) : RecyclerView.ViewHolder(binding.root) {
        private val desc = binding.tvDesc
        private val name = binding.tvName
        private val time = binding.tvTime
        private val profileImage = binding.ivProfile
        private val seeMore = binding.tvSeeMore
        private val seeLess = binding.tvSeeLess

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(reply: Replies) {
            desc.text = reply.isi
            name.text = reply.userName
            time.text = formatCreatedAt(reply.createdAt ?: "")

            val userId = reply.userId ?: -1
            profileImage.setOnClickListener { clickListener.onItemClicked(userId) }
            name.setOnClickListener { clickListener.onItemClicked(userId) }

            seeMore.setOnClickListener {
                desc.maxLines = Integer.MAX_VALUE
                seeMore.visibility = View.GONE
                seeLess.visibility = View.VISIBLE
            }

            seeLess.setOnClickListener {
                desc.maxLines = 5
                seeMore.visibility = View.VISIBLE
                seeLess.visibility = View.GONE
            }
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
        fun onItemClicked(userId: Int)
    }
}
