package com.giyas.newsfeed.ui.MainModule.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giyas.newsfeed.R
import com.giyas.newsfeed.entities.Article
import com.giyas.newsfeed.ui.NewsDetailsModule.NewsDetailsActivity

class ArticleListAdapter:ListAdapter<Article, ArticleListAdapter.ArticleViewHolder>(ArticleComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ArticleViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        private var ctx=context
        private val titleTV: TextView = itemView.findViewById(R.id.title_textView)
        private val subTitleTV: TextView = itemView.findViewById(R.id.sub_title_textview)
        private val news_iv: ImageView = itemView.findViewById(R.id.news_iv)
        fun bind(article: Article?) {
            titleTV.text = article?.title
            subTitleTV.text = article?.description
            Glide.with(itemView)
                .load(article?.urlToImage)
                .centerCrop()
                .into(news_iv)

            itemView.setOnClickListener {
                val intent = Intent(ctx, NewsDetailsActivity::class.java)
                intent.putExtra("Article",article)
                ctx.startActivity(intent)
            }
         }


        companion object {
            fun create(parent: ViewGroup): ArticleViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.each_news_row_layout, parent, false)
                return ArticleViewHolder(view,parent.context)
            }
        }
    }

    class ArticleComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem== newItem
        }
    }
}