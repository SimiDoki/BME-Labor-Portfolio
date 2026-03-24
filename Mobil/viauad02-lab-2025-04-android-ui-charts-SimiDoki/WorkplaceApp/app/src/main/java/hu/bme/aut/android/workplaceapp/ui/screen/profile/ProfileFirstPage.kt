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
fun ProfileFirstPage(
    name: String,
    email: String,
    address: String
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        InfoField(title = "NAME:", value = name)
        InfoField(title = "EMAIL:", value = email)
        InfoField(title = "ADDRESS:", value = address)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileFirstPage() {
    ProfileFirstPage(
        name = "Test User",
        email = "test@email",
        address = "Test Street"
    )
}
