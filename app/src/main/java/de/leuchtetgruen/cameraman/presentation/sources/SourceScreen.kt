package de.leuchtetgruen.cameraman.presentation.sources

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                 viewModel: SourceViewModel = hiltViewModel()) {
    Scaffold(bottomBar = { AppBottomNavigation(navController) }) {
        System.out.println(it)


        Column(Modifier.padding(16.dp)) {
            val style = MaterialTheme.typography.h4.copy(fontFamily = FontFamily(fonts = listOf(Font(R.font.rounded))))
            Text(text = stringResource(R.string.add_source_title), style = style, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .testTag(
                    TestTags.TAG_ADD_SOURCE_TITLE
                ))
            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text(stringResource(R.string.title)) },
                placeholder = { Text(stringResource(R.string.title))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 20.dp))
                    .testTag(TestTags.TAG_SOURCE_TITLE),
            )

            TextField(
                value = viewModel.url,
                onValueChange = { viewModel.url = it },
                label = { Text(stringResource(R.string.url)) },
                placeholder = { Text(stringResource(R.string.url))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 20.dp))
                    .testTag(TestTags.TAG_SOURCE_URL),
            )

            Button(onClick = {
                viewModel.createSource()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.create_source))
            }
        }
    }

}