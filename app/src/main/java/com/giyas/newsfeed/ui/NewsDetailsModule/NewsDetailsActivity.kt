package com.giyas.newsfeed.ui.NewsDetailsModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.giyas.newsfeed.R
import com.giyas.newsfeed.app.NewsFeedApplication
import com.giyas.newsfeed.entities.Article
import com.giyas.newsfeed.ui.MainModule.MainActivity
import com.giyas.newsfeed.ui.MainModule.viewModel.ArticleViewModel
import com.giyas.newsfeed.ui.MainModule.viewModel.ArticleViewModelFactory

class NewsDetailsActivity : AppCompatActivity() {
    private val articleViewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory((application as NewsFeedApplication).repository)
    }
    var toolbar_view: View? = null
    var title_tv: TextView? = null
    var back_iv: ImageView? = null
    var share_iv: ImageView? = null
    lateinit var news_iv: ImageView
    var news_title_tv: TextView? = null
    var Author_name_tv: TextView? = null
    var date_tv: TextView? = null
    var description_tv: TextView? = null
    var news_source_btn: Button? = null
    var favorite_iv: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val intent: Intent
        intent=getIntent()
        val article :Article
        article= intent.getParcelableExtra("Article")!!
        news_title_tv=findViewById(R.id.news_title_tv)
        Author_name_tv=findViewById(R.id.Author_name_tv)
        date_tv=findViewById(R.id.date_tv)
        description_tv=findViewById(R.id.description_tv)
        news_source_btn=findViewById(R.id.news_source_btn)
        news_iv=findViewById(R.id.news_iv)

        news_title_tv?.setText(article.title)
        Author_name_tv?.setText(article.author)
        val date=article.publishedAt?.split('T')
        date_tv?.setText(date?.get(0))
        description_tv?.setText(article.description)
        description_tv?.setText(article.description)
        news_source_btn?.setOnClickListener{
            val intentNewsSource = Intent(this, NewsSourceActivity::class.java)
            intentNewsSource.putExtra("URL",article.url)
            startActivity(intentNewsSource)
        }
        toolbar_view = findViewById(R.id.toolbar)
        title_tv = this.toolbar_view?.findViewById(R.id.toolbar_title)
        title_tv?.setText("")
        back_iv = this.toolbar_view?.findViewById(R.id.toolbar_backarrow)
        share_iv = this.toolbar_view?.findViewById(R.id.share_iv)
        favorite_iv = this.toolbar_view?.findViewById(R.id.favorite_iv)
        back_iv?.visibility = View.VISIBLE
        favorite_iv?.visibility = View.VISIBLE
        share_iv?.visibility = View.VISIBLE
        back_iv?.setOnClickListener { onBackPressed() }

        Glide.with(news_iv)
            .load(article.urlToImage)
            .into(news_iv)

        var result:Article?
        val title=article.title
        result=articleViewModel.getAllArticlesByTitle(title)
        if(result!=null)
        {
            favorite_iv!!.isChecked=true
        }
        share_iv?.setOnClickListener{
            val intentShare= Intent()
            intentShare.action=Intent.ACTION_SEND
            intentShare.putExtra(Intent.EXTRA_TEXT,"Article Link: "+article.url)
            intentShare.type="text/plain"
            startActivity(Intent.createChooser(intentShare,"Share To:"))
        }
        favorite_iv?.setOnClickListener{



            if (favorite_iv!!.isChecked)
            {
                articleViewModel.insert(article)

            }
            else
            {
                articleViewModel.delete(article)

            }


        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}