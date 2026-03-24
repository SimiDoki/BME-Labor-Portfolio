package hu.bme.aut.android.shoppinglist.ui.screen.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.shoppinglist.ShoppingListApplication
import hu.bme.aut.android.shoppinglist.data.entities.ShoppingItem
import hu.bme.aut.android.shoppinglist.data.repository.IShoppingItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ShoppingListViewModel(
    private val repository: IShoppingItemRepository
) : ViewModel() {
    private val _list = MutableStateFlow<List<ShoppingItem>>(listOf())
    val shoppingItemList = _list.asStateFlow()

    init {
        getAllItems()
    }

    fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems().collectLatest {
                _list.tryEmit(it)
            }
        }
    }

    fun insert(item: ShoppingItem) {
        viewModelScope.launch {
            try {
                repository.insert(shoppingItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(item: ShoppingItem) {
        viewModelScope.launch {
            try {
                repository.update(shoppingItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun delete(item: ShoppingItem) {
        viewModelScope.launch {
            try {
                repository.delete(shoppingItem = item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShoppingListViewModel(repository = ShoppingListApplication.repository)
            }
        }
    }
}
