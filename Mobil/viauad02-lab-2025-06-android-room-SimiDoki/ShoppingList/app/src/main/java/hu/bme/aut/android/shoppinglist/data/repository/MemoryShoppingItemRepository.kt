package hu.bme.aut.android.shoppinglist.data.repository

import androidx.compose.runtime.mutableStateListOf
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemoryShoppingItemRepository : IShoppingItemRepository {

    private var list = mutableStateListOf(
        ShoppingItem(
            id = 1,
            name = "Alma",
            description = "jonatán\n1 kg",
            estimatedPrice = 500,
            category = ShoppingItem.Category.FOOD,
            isBought = true
        ),
        ShoppingItem(
            id = 2,
            name = "A gyűrűk ura",
            description = "A gyűrű szövetsége",
            estimatedPrice = 8000,
            category = ShoppingItem.Category.BOOK,
            isBought = false
        )
    )

    override fun getAllItems(): Flow<List<ShoppingItem>> = flow {
            emit(list)
        }

    override suspend fun insert(shoppingItem: ShoppingItem) {
        delay(1000)
        list.add(shoppingItem.copy(id = (Long.MAX_VALUE*Math.random()).toLong()))
    }

    override suspend fun update(shoppingItem: ShoppingItem) {
        delay(1000)
        for (item in list) {
            if (item.id == shoppingItem.id)
                list[list.indexOf(item)] = shoppingItem
        }
    }

    override suspend fun delete(shoppingItem: ShoppingItem) {
        delay(1000)
        list.remove(shoppingItem)
    }
}
