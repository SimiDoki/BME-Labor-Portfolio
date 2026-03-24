package hu.bme.aut.android.workplaceapp.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.workplaceapp.ui.common.InfoField

@Composable
fun ProfileSecondPage(
    id: String,
    socialSecurityId: String,
    taxId: String,
    registrationId: String
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        InfoField(title = "ID:", value = id)
        InfoField(title = "SOCIAL SECURITY ID:", value = socialSecurityId)
        InfoField(title = "TAX ID:", value = taxId)
        InfoField(title = "REGISTRATION ID:", value = registrationId)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileSecondPage() {
    ProfileSecondPage(
        id = "123456",
        socialSecurityId = "A89FSE568TZ",
        taxId = "GO894GE56",
        registrationId = "R6879SDLTH"
    )
}
