package hu.bme.aut.android.simpledrawer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.simpledrawer.R
import hu.bme.aut.android.simpledrawer.ui.screen.DrawingMode
import hu.bme.aut.android.simpledrawer.ui.screen.DrawingViewModel

@Composable
fun BottomBar(viewModel: DrawingViewModel) {
    var showStyle by remember { mutableStateOf(false) }
    val drawingMode by viewModel.drawingMode.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showStyle = !showStyle },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_style),
                        contentDescription = stringResource(id = R.string.style)
                    )
                    //HT8QHM
                    DropdownMenu(
                        expanded = showStyle,
                        onDismissRequest = { showStyle = false}) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    stringResource(id = R.string.point),
                                    color = if (drawingMode == DrawingMode.POINT) Color.Magenta else Color.Black
                                )
                            },
                            onClick = {
                                viewModel.setDrawingMode(DrawingMode.POINT)
                                showStyle = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    stringResource(id = R.string.line),
                                    color = if (drawingMode == DrawingMode.LINE) Color.Magenta else Color.Black
                                )
                            },
                            onClick = {
                                viewModel.setDrawingMode(DrawingMode.LINE)
                                showStyle = false
                            }
                        )
                    }
                };
                IconButton(
                    onClick = { showDialog = true  },
                    modifier = Modifier.size(64.dp)
                ){
                    Icon(
                    painterResource(id =R.drawable.ic_clear_canvas),
                    contentDescription = stringResource(id = R.string.clear)
                    )
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(stringResource(id = R.string.clear)) },
                        text = { Text(stringResource(id = R.string.are_you_sure_want_to_clear)) },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog = false
                                viewModel.clearCanvas()
                            }) {
                                Text(stringResource(id = R.string.ok))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text(stringResource(id = R.string.cancel))
                            }
                        }
                    )
                }
            }
        },
        containerColor = Color(0xFF6200EE),
    )
}

@Composable
@Preview
fun PreviewBottomBar() {
    BottomBar(viewModel = viewModel())
}
