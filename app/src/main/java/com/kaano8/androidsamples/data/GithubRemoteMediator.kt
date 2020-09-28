package com.kaano8.androidsamples.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kaano8.androidsamples.api.paging.GithubService
import com.kaano8.androidsamples.api.paging.IN_QUALIFIER
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.models.paging.RemoteKeys
import com.kaano8.androidsamples.models.paging.Repo
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
const val GITHUB_STARTING_PAGE_INDEX = 1


@ExperimentalPagingApi
class GithubRemoteMediator(private val query: String, private val githubService: GithubService, private val appDatabase: AppDatabase): RemoteMediator<Int, Repo>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }
        val apiQuery = query + IN_QUALIFIER

        return try {
            val apiResponse = githubService.searchRepos(query, page, state.config.pageSize)

            val repos = apiResponse.items
            val endOfPaginationReached = repos.isEmpty()

            appDatabase.withTransaction {
                // Indicates that loading is happening for the first time
                if (loadType == LoadType.REFRESH) {
                    appDatabase.run {
                        repoDao.clearRepos()
                        remoteKeysDao().clearRemoteKeys()
                    }
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = repos.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                appDatabase.run {
                    repoDao.insertAll(repos)
                    remoteKeysDao().insertAll(keys)
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Repo>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Repo>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
}