package com.example.appsproduct.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appsproduct.data.remote.ApiService
import com.example.appsproduct.domain.model.Users
import com.example.appsproduct.data.converter.Converter
import com.example.appsproduct.data.local.dao.ProductDao
import com.example.appsproduct.data.local.dao.UserDao
import com.example.appsproduct.data.local.entity.ProductEntity

@Database(
    exportSchema = true, version = 1, entities = [ProductEntity::class, Users::class]
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "product_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}