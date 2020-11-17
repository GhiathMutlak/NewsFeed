package com.giyas.newsfeed.ui.MainModule.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giyas.newsfeed.R
import com.giyas.newsfeed.entities.Article
import com.giyas.newsfeed.ui.API.Articles
import com.giyas.newsfeed.ui.API.NewsData
import com.giyas.newsfeed.ui.API.RestNewsDataWithAuth
import com.giyas.newsfeed.ui.MainModule.MainActivity
import com.giyas.newsfeed.ui.MainModule.adapter.ArticleListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    var toolbar_view: View? = null
    var title_tv: TextView? = null
    var back_iv: ImageView? = null
    lateinit var searchView: SearchView
    lateinit var progress_circular: ProgressBar
    lateinit var recyclerview_news: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_news, container, false)
//        val textView: TextView = root.findViewById(R.id.text_news)
//        newsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        toolbar_view = root.findViewById(R.id.toolbar)
        progress_circular = root.findViewById(R.id.progress_circular)
        recyclerview_news = root.findViewById(R.id.recyclerview_news)
        title_tv = this.toolbar_view?.findViewById(R.id.toolbar_title)
        title_tv?.setText(getString(R.string.app_name))
        back_iv = this.toolbar_view?.findViewById(R.id.toolbar_backarrow)
        searchView = root.findViewById(R.id.searchView) as SearchView

        back_iv?.setOnClickListener({ (activity as MainActivity?)!!.onBackPressed() })
        searching(searchView)
        return root
    }


    fun  getNews(search: String)
    {
        var service: RestNewsDataWithAuth.GitApiInterface? = null
        service = RestNewsDataWithAuth.getArticle()
        val call: Call<NewsData>?
        call= service?.getNewsData(search,"90f78b5e459f4557a6d285161db89387")
        call?.enqueue(object : Callback<NewsData> {
            override fun onFailure(call: Call<NewsData>?, t: Throwable?) {
                Log.e(javaClass.simpleName, "loginResponse: $call")
                Log.e(javaClass.simpleName, "loginResponse: ${t?.printStackTrace()}")

            }

            override fun onResponse(call: Call<NewsData>?, response: Response<NewsData>?) {
                Log.e(javaClass.simpleName, "loginResponse: ${response?.raw()}")
                Log.e(javaClass.simpleName, "loginResponse: ${response?.code()}")
                Log.e(javaClass.simpleName, "loginResponse: ${response?.message()}")

                var newsdata:NewsData?=response?.body()
                val it: ListIterator<Articles> = newsdata?.articles!!.listIterator()
                val list = ArrayList<Article>()//Creating an empty arraylist
                while (it.hasNext()) {

                    val e:Articles = it.next()
                    var article=
                        Article(e.title,e.author,e.description,e.source?.name,e.url,e.urlToImage,e.publishedAt)

                    list.add(article)


                }
                val adapter = ArticleListAdapter()
                recyclerview_news.adapter = adapter
                recyclerview_news.layoutManager = LinearLayoutManager(context)
                adapter.submitList(list)


                progress_circular.visibility=View.GONE
                searchView.isEnabled=true
                if(list.size==0)
                {
                    Toast.makeText(context,"No result match search value",Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        if(!MainActivity.query.equals(""))
        searchView.setQuery( MainActivity.query,true)
    }

    private fun searching(search: SearchView?) {
        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("TAG", " query submit")
                progress_circular.visibility=View.VISIBLE
                searchView.isEnabled=false
                getNews(query)
                MainActivity.query=query
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("TAG", " query text change")
                return true
            }
        })
    }

    companion object {

        fun newInstance(): NewsFragment {
            return NewsFragment()

        }
    }
}