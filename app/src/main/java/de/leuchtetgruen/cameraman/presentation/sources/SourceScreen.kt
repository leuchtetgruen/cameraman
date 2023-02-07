package de.leuchtetgruen.cameraman.presentation.sources

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.presentation.navigation.AppBottomNavigation
import de.leuchtetgruen.cameraman.util.TestTags


@Composable
fun SourceScreen(navController: NavController,
                 viewModel: SourceScreenViewModel = hiltViewModel()) {
    Scaffold(bottomBar = { AppBottomNavigation(navController) }) {
        println(it)

        val ctx = LocalContext.current
        LaunchedEffect(Unit) {
            viewModel.showToastFlow.collect {
                Toast.makeText(ctx, ctx.getString(R.string.create_source_success), Toast.LENGTH_LONG).show()
            }
        }

        Column(Modifier.padding(16.dp)) {
            val style = MaterialTheme.typography.h4.copy(fontFamily = FontFamily(fonts = listOf(Font(R.font.rounded))))
            Text(text = stringResource(R.string.add_source_title), style = style, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .testTag(
                    TestTags.TAG_ADD_SOURCE_TITLE
                ))
            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { title -> viewModel.title =  title },
                label = { Text(stringResource(R.string.title)) },
                placeholder = { Text(stringResource(R.string.title))},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.TAG_SOURCE_TITLE),
            )

            if (viewModel.createResult.value == CreateSourceResult.TITLE_INVALID) {
                Text(stringResource(R.string.error_title_invalid), color = Color.Red, style = MaterialTheme.typography.caption, modifier = Modifier
                    .testTag(TestTags.TAG_ERROR_MESSAGE_TITLE))
            }

            OutlinedTextField(
                value = viewModel.url,
                onValueChange = { url -> viewModel.url = url },
                label = { Text(stringResource(R.string.url)) },
                placeholder = { Text(stringResource(R.string.url))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(top=16.dp))
                    .testTag(TestTags.TAG_SOURCE_URL),
            )

            if (viewModel.createResult.value == CreateSourceResult.URL_INVALID) {
                Text(stringResource(R.string.error_url_invalid), color = Color.Red, style = MaterialTheme.typography.caption,  modifier = Modifier
                    .testTag(TestTags.TAG_ERROR_MESSAGE_URL))
            }

            if (viewModel.createResult.value == CreateSourceResult.SERVER_SIDE_ERROR) {
                Text(stringResource(id = R.string.error_create_source_serverside), color = Color.Red, style = MaterialTheme.typography.caption,  modifier = Modifier
                    .testTag(TestTags.TAG_ERROR_MESSAGE))
            }

            if (viewModel.isLoading) {
                CircularProgressIndicator(Modifier.align(CenterHorizontally))
            }
            else {
                Button(onClick = {
                    viewModel.createSource()
                }, modifier = Modifier.fillMaxWidth().padding(PaddingValues(top=16.dp))) {
                    Text(text = stringResource(R.string.create_source))
                }
            }
        }
    }

}