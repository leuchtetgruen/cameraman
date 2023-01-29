package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ShotScreen(navController: NavController,
               id: String,
               viewModel: ShotViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.navController = navController
    viewModel.loadShot(id.toInt())

    val imageModel = { viewModel.shotDescription?.imageUrl }; // loading a network image using an URL.
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        GlideImage(
            imageModel = imageModel,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier.height(300.dp),
        )

        Spacer(modifier = Modifier.height(30.dp))

        val description = if (viewModel.shotDescription != null) viewModel.shotDescription?.description else "foo"
        Text(text = description ?: "bar")
    }
}