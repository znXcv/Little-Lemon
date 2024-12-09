package com.example.littlelemon.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity
data class MenuItemLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM MenuItemLocal")
    fun getAll(): List<MenuItemLocal>

    @Query("SELECT * FROM menuitemlocal WHERE title = :title")
    fun findByName(title: String): List<MenuItemLocal>

    @Insert
    fun insert(menuItems: MenuItemLocal)
}

@Database(
    entities = [MenuItemLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

class DatabaseRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "little_lemon.db"
    ).build()

    fun insertMenuItems(menuItems: MenuItemLocal) {
        db.userDao().insert(menuItems)
    }

    fun getAllMenuItems(): List<MenuItemLocal> {
        return db.userDao().getAll()
    }

    fun findMenuItem(title: String): List<MenuItemLocal> {
        return db.userDao().findByName(title)
    }
}
