package com.example.appsproduct.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.appsproduct.domain.model.Product
import com.example.appsproduct.data.remote.ApiService

class ProductPagingSource(private val apiService: ApiService) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: 0
        return try {
            val response = apiService.getProducts(params.loadSize, position)
            LoadResult.Page(
                data = response.products,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (response.products.isEmpty()) null else position + params.loadSize
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}