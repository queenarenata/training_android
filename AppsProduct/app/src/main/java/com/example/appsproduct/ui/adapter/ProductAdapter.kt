package com.example.appsproduct.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appsproduct.ui.fragment.DetailActivity
import com.example.appsproduct.databinding.ItemProductBinding
import com.example.appsproduct.domain.model.Product

class ProductAdapter(private val onItemClicked: (Product) -> Unit) :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(ProductComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product!!)
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onItemClicked: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productName.text = "${product.title}"
                productPrice.text = "Rp ${product.price}"
                productStock.text = "Stok: ${product.stock}"
                productThumbnail.load(product.thumbnail)

                root.setOnClickListener {
                    onItemClicked(product)
                }
            }
        }
    }

    companion object {
        private val ProductComparator = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}