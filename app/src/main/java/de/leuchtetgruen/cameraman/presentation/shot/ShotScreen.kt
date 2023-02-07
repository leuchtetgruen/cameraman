package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.util.TestTags

@Composable
fun ShotScreen(navController: NavController,
               id: String,
               viewModel: ShotViewModel = hiltViewModel()) {

    viewModel.navController = navController
    viewModel.loadShot(id.toInt())

    if  (viewModel.shotDescription == null) return
    
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        ShotImage(ShotImageViewModel(viewModel.shotDescription))

        Spacer(modifier = Modifier.height(30.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(20.dp))) {


            val style = MaterialTheme.typography.h5.copy(fontFamily = FontFamily(fonts = listOf(Font(R.font.rounded))))

            val title = stringResource(viewModel.shotDescription!!.titleStringId())

            Text(text = title, style = style, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().testTag(TestTags.TAG_SHOT_TITLE))

            Spacer(modifier = Modifier.height(30.dp))

            Text(text = viewModel.shotDescription!!.description, Modifier.testTag(TestTags.TAG_DESCRIPTION))

            Spacer(modifier = Modifier.weight(1f))

            //TODO export color to color scheme
            if (viewModel.saving) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            else {
                if (!viewModel.shotDescription!!.done) {
                    Button(onClick = { viewModel.markAsDone() }, modifier = Modifier.fillMaxWidth().testTag(TestTags.TAG_TODO_BTN), colors = ButtonDefaults.buttonColors(backgroundColor = Color(116, 166, 100), contentColor = Color.White)) {
                        Icon(painter = painterResource(id = R.drawable.check_white),
                            contentDescription = stringResource(R.string.checkmark),
                            modifier = Modifier.padding(end=16.dp))
                        Text(stringResource(R.string.mark_as_done))
                    }
                }
                else{
                    Button(onClick = { viewModel.markAsToDo() }, modifier = Modifier.fillMaxWidth().testTag(TestTags.TAG_DONE_BTN), colors = ButtonDefaults.buttonColors(backgroundColor = Color(244, 21, 73), contentColor = Color.White)) {
                        Icon(painter = painterResource(id = R.drawable.cancel_white),
                            contentDescription = stringResource(R.string.cancel_descr),
                            modifier = Modifier.padding(end=16.dp))
                        Text(stringResource(R.string.mark_as_todo))
                    }
                }
            }
        }
        


    }
}