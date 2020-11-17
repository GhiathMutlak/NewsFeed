package com.giyas.newsfeed.ui.NewsDetailsModule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.giyas.newsfeed.R
import com.giyas.newsfeed.entities.Article

class NewsSourceActivity : AppCompatActivity() {
    var webView: WebView? = null
    var toolbar_view: View? = null
    var title_tv: TextView? = null
    var back_iv: ImageView? = null
    var share_iv: ImageView? = null
    var favorite_iv: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_source)

        val intent: Intent
        intent=getIntent()

        val url=intent.getStringExtra("URL")

        webView = findViewById(R.id.webview)
        webView?.settings?.setJavaScriptEnabled(true)
        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView?.loadUrl(url.toString())
        toolbar_view = findViewById(R.id.toolbar)
        title_tv = this.toolbar_view?.findViewById(R.id.toolbar_title)
        title_tv?.setText("News Source")
        back_iv = this.toolbar_view?.findViewById(R.id.toolbar_backarrow)
        share_iv = this.toolbar_view?.findViewById(R.id.share_iv)
        favorite_iv = this.toolbar_view?.findViewById(R.id.favorite_iv)
        back_iv?.visibility = View.VISIBLE
        favorite_iv?.visibility = View.GONE
        share_iv?.visibility = View.GONE
        back_iv?.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}