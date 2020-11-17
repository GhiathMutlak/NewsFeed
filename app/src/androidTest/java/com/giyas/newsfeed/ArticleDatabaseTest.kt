package com.giyas.newsfeed

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.giyas.newsfeed.dao.ArticleDAO
import com.giyas.newsfeed.database.ArticleDatabase
import com.giyas.newsfeed.entities.Article
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDatabaseTest {

    private lateinit var articleDAO: ArticleDAO
    private lateinit var db: ArticleDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ArticleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        articleDAO = db.articleDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetArticle() {
        val article = Article()
        articleDAO.insert(article)
        val lastArticle = articleDAO.getLastArticle()
        Assert.assertEquals(lastArticle?.title, "1")
    }

}