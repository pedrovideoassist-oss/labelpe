package com.validatech.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.validatech.data.dao.*
import com.validatech.data.entities.*
import com.validatech.data.seed.DatabaseSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        OperatorEntity::class,
        RoleEntity::class,
        LocationEntity::class,
        CategoryEntity::class,
        ProductEntity::class,
        LabelEntity::class,
        RestaurantSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun operatorDao(): OperatorDao
    abstract fun roleDao(): RoleDao
    abstract fun locationDao(): LocationDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun labelDao(): LabelDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        fun build(context: Context): AppDatabase {
            val database = Room.databaseBuilder(context, AppDatabase::class.java, "validatech.db")
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                DatabaseSeeder.seed(database)
            }
            return database
        }
    }
}
