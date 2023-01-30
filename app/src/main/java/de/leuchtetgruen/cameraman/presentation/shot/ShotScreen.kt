package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R

@Composable
fun ShotScreen(navController: NavController,
               id: String,
               viewModel: ShotViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.navController = navController
    viewModel.loadShot(id.toInt())

    if  (viewModel.shotDescription == null) return
    
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        ShotImage(shotDescription = viewModel.shotDescription)

        Spacer(modifier = Modifier.height(30.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(20.dp))) {


            val style = MaterialTheme.typography.h4.copy(fontFamily = FontFamily(fonts = listOf(Font(R.font.rounded))))

            val title = if (viewModel.shotDescription != null) viewModel.shotDescription?.title() else "Shot Description"
            Text(text = title!!, style = style, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(30.dp))

            val description = if (viewModel.shotDescription != null) viewModel.shotDescription?.description else "foo"
            Text(text = description ?: "bar")

            Spacer(modifier = Modifier.weight(1f))

            //TODO export color to color scheme
            if (viewModel.saving) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            else {
                if (!viewModel.shotDescription!!.done) {
                    Button(onClick = { viewModel.markAsDone() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = Color(116, 166, 100), contentColor = Color.White)) {
                        Icon(painter = painterResource(id = R.drawable.check_white),
                            contentDescription = "Check mark",
                            modifier = Modifier.padding(end=16.dp))
                        Text("Als erledigt markieren")
                    }
                }
                else{
                    Button(onClick = { viewModel.markAsToDo() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = Color(244, 21, 73), contentColor = Color.White)) {
                        Icon(painter = painterResource(id = R.drawable.cancel_white),
                            contentDescription = "Check mark",
                            modifier = Modifier.padding(end=16.dp))
                        Text("Auf nicht erledigt setzen")
                    }
                }
            }
        }
        


    }
}