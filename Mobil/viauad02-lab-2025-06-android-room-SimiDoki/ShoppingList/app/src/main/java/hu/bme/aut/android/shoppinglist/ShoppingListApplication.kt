package hu.bme.aut.android.shoppinglist

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.shoppinglist.data.repository.IShoppingItemRepository
import hu.bme.aut.android.shoppinglist.data.repository.RoomShoppingItemRepository
import hu.bme.aut.android.shoppinglist.data.database.ShoppingListDatabase

class ShoppingListApplication : Application() {

    companion object {

        lateinit var repository: IShoppingItemRepository

        private lateinit var database: ShoppingListDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            ShoppingListDatabase::class.java,
            "shoppinglist_database"
        ).fallbackToDestructiveMigration(false).build()

        repository = RoomShoppingItemRepository(database.shoppingItemDao)

        //repository = MemoryShoppingItemRepository()
    }
}
