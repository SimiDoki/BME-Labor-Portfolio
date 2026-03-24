package hu.bme.aut.android.workplaceapp.ui.screen.holiday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HolidayViewModel : ViewModel() {
    private val _holidayMaxValue = MutableStateFlow(20)
    val maxHolidayValue: StateFlow<Int> = _holidayMaxValue

    private val _takenHolidayValue = MutableStateFlow(15)
    val takenHolidayValue: StateFlow<Int> = _takenHolidayValue

    fun takeHoliday(days: Int) {
        viewModelScope.launch {
            _takenHolidayValue.value += days
        }
    }
}
