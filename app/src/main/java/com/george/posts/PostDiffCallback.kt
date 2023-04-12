package com.george.posts

import androidx.recyclerview.widget.DiffUtil
import com.george.posts.model.PostUIM

class PostDiffCallback : DiffUtil.ItemCallback<PostUIM>() {

    override fun areItemsTheSame(oldItem: PostUIM, newItem: PostUIM) = oldItem == newItem

    override fun areContentsTheSame(oldItem: PostUIM, newItem: PostUIM) = oldItem.id == newItem.id
}