package hu.bme.aut.android.shoppinglist.data.repository

import hu.bme.aut.android.shoppinglist.data.dao.ShoppingItemDao
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

class RoomShoppingItemRepository(private val dao: ShoppingItemDao) : IShoppingItemRepository {

    override fun getAllItems(): Flow<List<ShoppingItem>> = dao.getAll()
    override suspend fun insert(shoppingItem: ShoppingItem) = dao.insert(shoppingItem)
    override suspend fun update(shoppingItem: ShoppingItem) = dao.update(shoppingItem)
    override suspend fun delete(shoppingItem: ShoppingItem) = dao.delete(shoppingItem)
}
