package ru.mirea.computerclub.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.dtos.ComputerDto
import ru.mirea.computerclub.domain.entities.Computer
import java.io.IOException

class ComputerPagingSource(
    private val repository: ComputerClubRepository,
    private val computerMapper: Mapper<ComputerDto, Computer>,
    private val query: String?
) : PagingSource<Int, Computer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Computer> {
        return try {
            val page = params.key ?: 1
            val response = if (query == null) repository.getAllComputers(page) else repository.searchComputers(query, page)
            val body = response.body()?.map { computerMapper.map(it) }

            return if (response.isSuccessful && body != null) {
                LoadResult.Page(
                    data = body,
                    prevKey = null,
                    nextKey = if (body.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Response body invalid"))
            }

        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Computer>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    companion object {
        const val networkPageSize = 10
        const val initialLoad = 3
        const val prefetchDistance = 1
    }
}