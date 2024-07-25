package com.example.appscatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        barangAdapter = BarangAdapter(this, barangList())
        recyclerView.adapter = barangAdapter

        val switchMode: Switch = findViewById(R.id.switchMode)
        switchMode.isChecked = isNightMode()

        switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun barangList(): List<Barang> {
        val name = resources.getStringArray(R.array.item_names)
        val description = resources.getStringArray(R.array.item_descriptions)
        val price = resources.getStringArray(R.array.item_prices)
        val stock = resources.getIntArray(R.array.item_stocks)
        val photo = resources.obtainTypedArray(R.array.item_images)
        val listBarang = ArrayList<Barang>()
        for (i in name.indices) {
            val barang = Barang(
                name[i],
                photo.getResourceId(i,-1),
                description[i],
                price[i].toDouble(),
                stock[i],)
            listBarang.add(barang)
        }
        return listBarang
    }

    private fun isNightMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

}