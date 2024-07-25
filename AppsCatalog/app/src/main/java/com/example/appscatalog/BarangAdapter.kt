package com.example.appscatalog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

class BarangAdapter(private val context: Context, private val barangList: List<Barang>) :
    RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        holder.bind(barang)
    }

    override fun getItemCount(): Int {
        return barangList.size
    }

    inner class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.barang_name)
        private val imageView: ImageView = itemView.findViewById(R.id.barang_image)
        private val priceTextView: TextView = itemView.findViewById(R.id.barang_price)
        private val stockTextView: TextView = itemView.findViewById(R.id.barang_stock)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedBarang = barangList[position]
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra("BARANG_NAME", clickedBarang.name)
                        putExtra("BARANG_IMAGE", clickedBarang.imageResource)
                        putExtra("BARANG_DESCRIPTION", clickedBarang.description)
                        putExtra("BARANG_PRICE", clickedBarang.price)
                        putExtra("BARANG_STOCK", clickedBarang.stock)
                    }
                    context.startActivity(intent)
                }
            }
        }

        fun bind(barang: Barang) {
            nameTextView.text = barang.name
            imageView.setImageResource(barang.imageResource)
            priceTextView.text = formatRupiah(barang.price)
            stockTextView.text = "Stok: ${barang.stock}"
        }

        private fun formatRupiah(number: Double): String {
            val localeID = Locale("in", "ID")
            val format = NumberFormat.getCurrencyInstance(localeID)
            return format.format(number)
        }
    }
}