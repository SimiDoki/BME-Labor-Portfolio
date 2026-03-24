package hu.bme.aut.android.workplaceapp.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.workplaceapp.R
import hu.bme.aut.android.workplaceapp.ui.common.ImageButton
import hu.bme.aut.android.workplaceapp.ui.common.TopBar

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onProfileButtonClick: () -> Unit,
    onHolidayButtonClick: () -> Unit,
    onSalaryButtonClick: () -> Unit,
    onCafeteriaButtonClick: () -> Unit
) {
    Scaffold (
        topBar = { TopBar(label = stringResource(id = R.string.app_name)) }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ImageButton(
                        onClick = onProfileButtonClick,
                        modifier = modifier,
                        label = stringResource(R.string.label_profile),
                        painter = painterResource(id = R.drawable.profile),
                        size = 160.dp,
                        contentDescription = stringResource(R.string.label_profile)
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    ImageButton(
                        onClick = onSalaryButtonClick,
                        modifier = modifier,
                        label = stringResource(R.string.label_salary),
                        painter = painterResource(id = R.drawable.payment),
                        size = 160.dp,
                        contentDescription = stringResource(R.string.label_salary)
                    )

                }
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                //HT8QHM
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ImageButton(
                        onClick = onHolidayButtonClick,
                        modifier = modifier,
                        label = stringResource(R.string.label_holiday),
                        painter = painterResource(id = R.drawable.holiday),
                        size = 160.dp,
                        contentDescription = stringResource(R.string.label_holiday)
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    ImageButton(
                        onClick = onCafeteriaButtonClick,
                        modifier = modifier,
                        label = stringResource(R.string.label_cafeteria),
                        painter = painterResource(id = R.drawable.cafeteria),
                        size = 160.dp,
                        contentDescription = stringResource(R.string.label_cafeteria)
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        onProfileButtonClick = {},
        onHolidayButtonClick = {},
        onCafeteriaButtonClick = {},
        onSalaryButtonClick = {}
    )
}
