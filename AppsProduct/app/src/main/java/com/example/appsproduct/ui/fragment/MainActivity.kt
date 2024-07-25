package com.example.appsproduct.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsproduct.R
import com.example.appsproduct.ui.adapter.ProductAdapter
import com.example.appsproduct.databinding.ActivityMainBinding
import com.example.appsproduct.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()

    private val productAdapter = ProductAdapter { product ->
        val intent = Intent(this, DetailActivity::class.java).apply {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}