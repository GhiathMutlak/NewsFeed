package com.giyas.newsfeed.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.giyas.newsfeed.entities.Article
import kotlinx.coroutines.flow.Flow
@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: Article)

    @Update
    fun update(article: Article)

    @Delete
    fun delete(article: Article)

    @Query("delete from articles")
    suspend fun deleteAll()

    @Query("SELECT  * FROM articles ORDER BY published_at DESC")
    fun getAllArticlesLiveData(): LiveData<List<Article>>

    @Query("SELECT * FROM articles ORDER BY published_at DESC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles Where title Like :titleStr LIMIT 1")
    fun getAllArticlesByTitle(titleStr:String?): Article?

    @Query("SELECT * FROM articles ORDER BY published_at DESC LIMIT 1")
    fun getLastArticle(): Article?

}