package com.example.appscatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Mengikuti mode sistem
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        val detailName: TextView = findViewById(R.id.detail_name)
        val detailImage: ImageView = findViewById(R.id.detail_image)
        val detailDescription: TextView = findViewById(R.id.detail_description)
        val detailPrice: TextView = findViewById(R.id.detail_price)
        val detailStock: TextView = findViewById(R.id.detail_stock)

        val name = intent.getStringExtra("BARANG_NAME")
        val imageResource = intent.getIntExtra("BARANG_IMAGE", 0)
        val description = intent.getStringExtra("BARANG_DESCRIPTION")
        val price = intent.getDoubleExtra("BARANG_PRICE", 0.0)
        val stock = intent.getIntExtra("BARANG_STOCK", 0)

        detailName.text = name
        detailImage.setImageResource(imageResource)
        detailDescription.text = description
        detailPrice.text = "Harga: ${formatRupiah(price)}"
        detailStock.text = "Stok: $stock"
    }

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localeID)
        return format.format(number)
    }
}