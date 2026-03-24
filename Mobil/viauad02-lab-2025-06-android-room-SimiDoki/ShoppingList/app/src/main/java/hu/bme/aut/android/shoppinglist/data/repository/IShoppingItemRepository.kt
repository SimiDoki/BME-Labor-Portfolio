package hu.bme.aut.android.shoppinglist.data.repository

import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface IShoppingItemRepository {

    fun getAllItems(): Flow<List<ShoppingItem>>
    suspend fun insert(shoppingItem: ShoppingItem)
    suspend fun update(shoppingItem: ShoppingItem)
    suspend fun delete(shoppingItem: ShoppingItem)
}
