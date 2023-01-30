package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

import de.leuchtetgruen.cameraman.R

@Composable
fun ShotScreen(navController: NavController,
               id: String,
               viewModel: ShotViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.navController = navController
    viewModel.loadShot(id.toInt())
    
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (viewModel.shotDescription?.imageUrl != null) {
            GlideImage(
                imageModel = { viewModel.shotDescription?.imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.height(300.dp),
            )
        }
        else {
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.default_image_shot),
                contentDescription = "Placeholder image for shot",
                modifier = Modifier.height(300.dp), )
        }

        Spacer(modifier = Modifier.height(30.dp))




        val title = if (viewModel.shotDescription != null) viewModel.shotDescription?.title() else "Shot Description"
        Text(text = title!!, style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(30.dp))

        val description = if (viewModel.shotDescription != null) viewModel.shotDescription?.description else "foo"
        Text(text = description ?: "bar")
    }
}