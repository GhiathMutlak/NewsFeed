package com.giyas.newsfeed.entities

import androidx.annotation.WorkerThread
import com.giyas.newsfeed.dao.ArticleDAO
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ArticleRepository(private val articleDAO: ArticleDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allArticles: Flow<List<Article>> = articleDAO.getAllArticles()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(article: Article) {
        articleDAO.insert(article)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(article: Article) {
        articleDAO.delete(article)
    }


    fun getAllArticlesByTitle(titleStr:String?): Article? {
       return articleDAO.getAllArticlesByTitle(titleStr)
    }

}