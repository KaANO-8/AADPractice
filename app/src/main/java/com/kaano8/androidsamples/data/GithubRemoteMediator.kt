package com.kaano8.androidsamples.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kaano8.androidsamples.api.paging.GithubService
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.models.paging.Repo

@ExperimentalPagingApi
class GithubRemoteMediator(private val query: String, private val githubService: GithubService, private val appDatabase: AppDatabase): RemoteMediator<Int, Repo>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        TODO("Not yet implemented")
    }
}