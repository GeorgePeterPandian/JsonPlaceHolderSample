package com.george.posts

import androidx.recyclerview.widget.RecyclerView
import com.george.posts.databinding.PostsVhBinding
import com.george.posts.model.PostUIM

class PostsViewHolder(viewBinding: PostsVhBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    private val layout = viewBinding

    fun onBind(postUIM: PostUIM) {
        with(layout) {
            uim = postUIM
            executePendingBindings()
        }
    }
}