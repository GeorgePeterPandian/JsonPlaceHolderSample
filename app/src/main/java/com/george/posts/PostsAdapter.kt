package com.george.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.george.posts.databinding.PostsVhBinding
import com.george.posts.model.PostUIM


class PostsAdapter : ListAdapter<PostUIM, PostsViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        PostsVhBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}