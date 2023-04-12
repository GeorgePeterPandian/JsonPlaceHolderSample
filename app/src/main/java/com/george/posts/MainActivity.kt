package com.george.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.george.core.UIStates
import com.george.posts.databinding.ActivityMainBinding
import com.george.posts.model.PostUIM
import com.george.posts.viewModel.LoadPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: LoadPostsViewModel by viewModels()

    private lateinit var adapter: PostsAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
        setUpUI()
    }

    private fun setupRecyclerView() {
        with(binding) {
            adapter = PostsAdapter().also { recyclerView.adapter = it }
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun setUpUI() {
        lifecycleScope.launch {
            viewModel.loadPosts()
            viewModel.uiState.collect { updateUI(it) }
        }
    }

    private fun updateUI(uiStates: UIStates) {
        when {
            uiStates.isLoading -> onShowLoading()
            uiStates.success != null -> onShowData(uiStates.success as List<PostUIM>?)
            uiStates.error.isNotEmpty() -> onShowError(uiStates.error)
            else -> onShowLoading()
        }
    }

    private fun onShowData(postUIMS: List<PostUIM>?) {
        with(binding) {
            loading.visibility = GONE
            val posts = postUIMS.orEmpty()
            when {
                posts.isNotEmpty() -> {
                    recyclerView.visibility = VISIBLE
                    adapter.submitList(posts)
                }
                else -> {
                    recyclerView.visibility = GONE
                    Toast.makeText(this@MainActivity, "No data to display", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onShowLoading() {
        with(binding) {
            loading.visibility = VISIBLE
            recyclerView.visibility = GONE
        }
    }

    private fun onShowError(errorMessage: String?) {
        with(binding) {
            loading.visibility = GONE
            recyclerView.visibility = GONE
        }
        Toast.makeText(this, errorMessage.orEmpty(), Toast.LENGTH_SHORT).show()
    }
}