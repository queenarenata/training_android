package com.example.appsproduct.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.appsproduct.data.local.entity.ProductEntity
import com.example.appsproduct.databinding.ItemProductBinding
import com.example.appsproduct.domain.model.Product

class FavoriteAdapter(private val onItemClick: (ProductEntity) -> Unit) :
    PagingDataAdapter<ProductEntity, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product!!)
    }

    inner class FavoriteViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductEntity) {
            binding.apply {
                productName.text = product.name
                productPrice.text = "Rp ${product.price}"
                productStock.text = "Stok: ${product.stock}"
                productThumbnail.load(product.image)

                root.setOnClickListener { onItemClick(product) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }
    }
}