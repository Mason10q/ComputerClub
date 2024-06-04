package ru.mirea.computerclub.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.mirea.computerclub.core.Mapper
import ru.mirea.computerclub.data.ComputerClubRepository
import ru.mirea.computerclub.data.network.dtos.PurchaseDto
import ru.mirea.computerclub.domain.entities.Purchase
import java.io.IOException

class PurchasePagingSource(
    private val repository: ComputerClubRepository,
    private val purchaseMapper: Mapper<PurchaseDto, Purchase>
) : PagingSource<Int, Purchase>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Purchase> {
        return try {
            val page = params.key ?: 1
            val response = repository.getPurchaseHistory(page)
            val body = response.body()?.map(purchaseMapper::map)

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
            Log.d("asd", ex.message.toString())
            LoadResult.Error(ex)
        } catch (ex: IOException) {
            Log.d("asd", ex.message.toString())
            LoadResult.Error(ex)
        } catch (ex: Exception) {
            Log.d("asd", ex.message.toString())
            LoadResult.Error(ex)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Purchase>): Int? =
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