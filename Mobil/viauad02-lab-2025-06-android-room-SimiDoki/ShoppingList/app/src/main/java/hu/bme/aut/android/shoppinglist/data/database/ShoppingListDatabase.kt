package hu.bme.aut.android.shoppinglist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.shoppinglist.data.dao.ShoppingItemDao
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
@TypeConverters(value = [ShoppingItem.Category::class])
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract val shoppingItemDao: ShoppingItemDao
}
