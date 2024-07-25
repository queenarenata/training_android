package com.example.appsproduct.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.appsproduct.R
import com.example.appsproduct.data.local.AppDatabase
import com.example.appsproduct.data.local.entity.ProductEntity
import com.example.appsproduct.databinding.ActivityDetailBinding
import com.example.appsproduct.domain.model.Product
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getDatabase(this)

        product = Product(
            id = intent.getIntExtra("PRODUCT_ID", 0),
            title = intent.getStringExtra("PRODUCT_NAME") ?: "",
            description = intent.getStringExtra("PRODUCT_DESCRIPTION") ?: "",
            price = intent.getDoubleExtra("PRODUCT_PRICE", 0.0),
            stock = intent.getIntExtra("PRODUCT_STOCK", 0),
            thumbnail = intent.getStringExtra("PRODUCT_THUMBNAIL") ?: "",
            rating = 0.0,
            review = ""
        )

        binding.productName.text = product.title
        binding.productDescription.text = product.description
        binding.productPrice.text = "Rp ${product.price}"
        binding.productStock.text = "Stok: ${product.stock}"
        binding.productThumbnail.load(product.thumbnail)

        lifecycleScope.launch {
            val favorites = appDatabase.productDao().getAllFavorites().first()
            val isFavorite = favorites.any { it.id == product.id }
            updateFavoriteIcon(isFavorite)
        }

        binding.favoriteIcon.setOnClickListener {
            lifecycleScope.launch {
                val favorites = appDatabase.productDao().getAllFavorites().first()
                val isFavorite = favorites.any { it.id == product.id }
                if (isFavorite) {
                    appDatabase.productDao().delete(
                        ProductEntity(
                            product.id, product.title, product.description,
                            product.price.toString(), product.stock.toString(),product.thumbnail
                        )
                    )
                } else {
                    appDatabase.productDao().insert(
                        ProductEntity(
                            product.id, product.title, product.description,
                            product.price.toString(), product.stock.toString(),product.thumbnail
                        )
                    )
                }
                updateFavoriteIcon(!isFavorite)
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.favoriteIcon.setImageResource(R.drawable.ic_unfavorite)
        }
    }
}