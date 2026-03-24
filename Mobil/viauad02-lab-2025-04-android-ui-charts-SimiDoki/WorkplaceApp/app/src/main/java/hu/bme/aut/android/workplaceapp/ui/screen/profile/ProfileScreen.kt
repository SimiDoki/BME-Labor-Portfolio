package hu.bme.aut.android.workplaceapp.ui.screen.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.workplaceapp.R
import hu.bme.aut.android.workplaceapp.data.DataManager
import hu.bme.aut.android.workplaceapp.ui.common.TopBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBar(stringResource(id = R.string.label_profile))
        }
    ) { innerPadding ->
        val pagerState = rememberPagerState(pageCount = { 2 })
        val profile = DataManager.person

        HorizontalPager(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = pagerState
        ) {

            when (it) {
                0 -> {
                    ProfileFirstPage(
                        name = profile.name,
                        email = profile.email,
                        address = profile.address
                    )
                }

                1 -> {
                    ProfileSecondPage(
                        id = profile.id,
                        socialSecurityId = profile.socialSecurityNumber,
                        taxId = profile.taxId,
                        registrationId = profile.registrationId
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewProfileScreen() {
    ProfileScreen()
}
