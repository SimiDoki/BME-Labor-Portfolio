package hu.bme.aut.android.weatherinfo.ui.screen.citylist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.weatherinfo.R
import hu.bme.aut.android.weatherinfo.ui.common.StringInputDialog
import hu.bme.aut.android.weatherinfo.ui.screen.citylist.components.CityCard
import hu.bme.aut.android.weatherinfo.ui.screen.citylist.event.CityListScreenEvent
import hu.bme.aut.android.weatherinfo.ui.screen.citylist.state.CityListScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    modifier: Modifier = Modifier,
    onCityClick: (String) -> Unit,
    viewModel: CityListViewModel = viewModel(factory = CityListViewModel.Factory)
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    var isAddCityDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    isAddCityDialogOpen = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        when (state) {
            is CityListScreenState.Loading -> CircularProgressIndicator()
            is CityListScreenState.Error -> {
                Text(text = state.error.toString())
            }

            is CityListScreenState.Result -> {
                LazyColumn(
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(8.dp)
                ) {
                    items(items = state.cityList, key = { item -> item.id }) { city ->
                        CityCard(
                            city = city.name,
                            onCityClick = onCityClick,
                            onDelete = { viewModel.onEvent(CityListScreenEvent.DeleteCity(it)) }
                        )
                        if (state.cityList.size - 1 > state.cityList.indexOf(city)) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        if (isAddCityDialogOpen) {

            StringInputDialog(
                onDismiss = { isAddCityDialogOpen = false },
                onConfirm = { city ->
                    viewModel.onEvent(CityListScreenEvent.CityCreated(city))
                    isAddCityDialogOpen = false
                },
                title = stringResource(id = R.string.label_add_city),
                label = stringResource(id = R.string.label_city)
            )

        }
    }
}

@Composable
@Preview
fun CityListScreenPreview() {
    CityListScreen(onCityClick = {})
}
