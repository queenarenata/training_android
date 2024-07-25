package com.example.appsproduct.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appsproduct.R
import com.example.appsproduct.data.local.entity.ProductEntity
import com.example.appsproduct.ui.adapter.FavoriteAdapter
import com.example.appsproduct.domain.model.Product
import com.example.appsproduct.data.remote.ApiConfig
import com.example.appsproduct.data.repository.ProductRepository
import com.example.appsproduct.ui.viewmodel.ProductViewModel
import com.example.appsproduct.ui.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val repos = ProductRepository(ApiConfig.instance,requireContext())
        val factory = ProductViewModelFactory(repos)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.favorite_recycler_view)
        favoriteAdapter = FavoriteAdapter { product ->
            navigateToDetail(product)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        lifecycleScope.launch {
            productViewModel.listFavorites.collectLatest { favorites ->
                favoriteAdapter.submitData(favorites)
            }
        }
        return view
    }

    private fun navigateToDetail(product: ProductEntity) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("PRODUCT_ID", product.id)
            putExtra("PRODUCT_NAME", product.name)
            putExtra("PRODUCT_DESCRIPTION", product.description)
            putExtra("PRODUCT_PRICE", product.price)
            putExtra("PRODUCT_STOCK", product.stock)
            putExtra("PRODUCT_THUMBNAIL", product.image)
        }
        startActivity(intent)
    }
}