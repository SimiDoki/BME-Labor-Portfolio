package hu.bme.aut.android.androidwallet.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.androidwallet.R

@Composable
fun SalaryCard(isIncome: Boolean, item: String, price: String,onDelete: () -> Unit={}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = if (isIncome) R.drawable.ic_income else R.drawable.ic_expense),
            contentDescription = "Income/Expense"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = item,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = price,
                color = Color.Gray,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        //HT8QHM
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                onDelete()
            }
        ) {
            Image(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete button",
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewIncomeSalaryCard() {
    SalaryCard(isIncome = true, item = "item", price = "500 Ft")
}

@Composable
@Preview(showBackground = true)
fun PreviewExpenseSalaryCard() {
    SalaryCard(isIncome = false, item = "item", price = "500 Ft")
}
