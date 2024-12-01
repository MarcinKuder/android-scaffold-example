@file:OptIn(ExperimentalMaterial3Api::class)

package pl.edu.ansnt.aplikacjemobilne

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.edu.ansnt.aplikacjemobilne.ui.theme.AplikacjeMobilneTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplikacjeMobilneTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    topBar = {
                        MyTopBar()
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    bottomBar = {
                        MyBottomBar(scope, snackbarHostState)
                    }
                ) { innerPadding ->
                    MyContent(innerPadding)
                }
            }
        }
    }
}

@Composable
fun MyTopBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var menuExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth())
    {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    stringResource(R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            actions = {
                Box {
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edytuj") },
                            onClick = { /* todo */ },
                            leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Ustawienia") },
                            onClick = { /* todo */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = null
                                )
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Wyślij") },
                            onClick = { /* todo */ },
                            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                            trailingIcon = { Text("F10", textAlign = TextAlign.Center) }
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior,
        )
    }
}

@Composable
fun MyBottomBar(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    val openAlertDialog = remember { mutableStateOf(false) }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Hello!") } }) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = null
                )
            }
            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("World!") } }) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = null,
                )
            }
            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("!!!") } }) {
                Icon(
                    Icons.Filled.AccountBox,
                    contentDescription = null,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openAlertDialog.value = !openAlertDialog.value }) {
                Icon(Icons.Default.ThumbUp, contentDescription = null)
            }
        }
    )

    if (openAlertDialog.value) {
        AlertDialogExample(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = { openAlertDialog.value = false },
            dialogTitle = stringResource(R.string.dialog_title),
            dialogText = stringResource(R.string.dialog_content),
            icon = Icons.Default.Star,
        )
    }
}

@Composable
fun MyContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        repeat(15) {
            Card(
                modifier = Modifier
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Lorem ipsum dolor sit amet consectetur adipiscing elit",
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "https://developer.android.com/develop/ui/compose/documentation",
            modifier = Modifier.padding(7.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = null) },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }
            ) {
                Text("Anuluj")
            }
        }
    )
}