package com.example.appsproduct.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsproduct.ui.adapter.FavoriteAdapter
import com.example.appsproduct.ui.adapter.ProductAdapter
import com.example.appsproduct.databinding.FragmentHomeBinding
import com.example.appsproduct.domain.model.Product
import com.example.appsproduct.data.remote.ApiConfig
import com.example.appsproduct.data.remote.RetrofitInstance
import com.example.appsproduct.data.repository.ProductRepository
import com.example.appsproduct.ui.fragment.HomeFragmentDirections
import com.example.appsproduct.ui.viewmodel.ProductViewModel
import com.example.appsproduct.ui.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private var productAdapter = ProductAdapter { product ->
        navigateToDetail(product)
    }
    private val favoritesAdapter = FavoriteAdapter { product ->
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repos = ProductRepository(RetrofitInstance.api,requireContext())
        val factory = ProductViewModelFactory(repos)
        viewModel = ViewModelProvider(this,factory)[ProductViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.products.collectLatest { pagingData ->
                    productAdapter.submitData(pagingData)
                    Log.e("HomeFragment", "Data loaded")
                    Toast.makeText(requireContext(), "Data loaded", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                Log.e("HomeFragment", "onViewCreated: ${e.message}")
            }
        }

        productAdapter.addLoadStateListener { loadState ->
            binding.progressBar.visibility = if (loadState.refresh is androidx.paging.LoadState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        binding.fabFavorites.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFragmentFavorite()
            findNavController().navigate(action)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun navigateToDetail(product: Product) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("PRODUCT_ID", product.id)
            putExtra("PRODUCT_NAME", product.title)
            putExtra("PRODUCT_DESCRIPTION", product.description)
            putExtra("PRODUCT_PRICE", product.price)
            putExtra("PRODUCT_STOCK", product.stock)
            putExtra("PRODUCT_THUMBNAIL", product.thumbnail)
            putExtra("PRODUCT_RATING", product.rating)
            putExtra("PRODUCT_REVIEW", product.review)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}