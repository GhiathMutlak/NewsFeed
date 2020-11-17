package com.giyas.newsfeed.ui.MainModule.Favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giyas.newsfeed.ui.MainModule.MainActivity
import com.giyas.newsfeed.R
import com.giyas.newsfeed.app.NewsFeedApplication
import com.giyas.newsfeed.entities.Article
import com.giyas.newsfeed.ui.MainModule.adapter.ArticleListAdapter
import com.giyas.newsfeed.ui.MainModule.viewModel.ArticleViewModel
import com.giyas.newsfeed.ui.MainModule.viewModel.ArticleViewModelFactory

class FavoriteFragment : Fragment() {


    private val wordViewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory((activity?.application as NewsFeedApplication).repository)
    }
    var toolbar_view: View? = null
    var title_tv: TextView? = null
    var back_iv: ImageView? = null
    val adapter=ArticleListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        toolbar_view = root.findViewById(R.id.toolbar)
        title_tv = this.toolbar_view?.findViewById(R.id.toolbar_title)
        title_tv?.text = getString(R.string.title_favorites)
        back_iv = this.toolbar_view?.findViewById(R.id.toolbar_backarrow)
        back_iv?.setOnClickListener { (activity as MainActivity?)!!.onBackPressed() }
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allArticle.observe(owner = activity as MainActivity) { article ->
            // Update the cached copy of the words in the adapter.
            article.let { adapter.submitList(it) }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        wordViewModel.allArticle.observe(owner = activity as MainActivity) { article ->
            // Update the cached copy of the words in the adapter.
            article.let { adapter.submitList(it) }
        }

    }

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}