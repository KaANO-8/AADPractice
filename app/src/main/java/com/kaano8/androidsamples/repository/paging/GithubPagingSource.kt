package com.kaano8.androidsamples.repository.paging

import androidx.paging.PagingSource
import com.kaano8.androidsamples.api.paging.GithubService
import com.kaano8.androidsamples.api.paging.IN_QUALIFIER
import com.kaano8.androidsamples.models.paging.Repo
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(private val githubService: GithubService, private val query: String) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val result = githubService.searchRepos(apiQuery, position, params.loadSize)
            val repos = result.items
            LoadResult.Page(
                    data = repos,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}