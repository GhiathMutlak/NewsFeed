package com.giyas.newsfeed.ui.MainModule.viewModel

import androidx.lifecycle.*
import com.giyas.newsfeed.entities.Article
import com.giyas.newsfeed.entities.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: ArticleRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allArticle: LiveData<List<Article>> = repository.allArticles.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }


    fun getAllArticlesByTitle(titleStr:String?): Article? {
        return  repository.getAllArticlesByTitle(titleStr)
    }

}

class ArticleViewModelFactory(private val repository: ArticleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}