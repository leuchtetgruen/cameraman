package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.util.TestTags


@Composable
fun ShotImage(viewModel: ShotImageViewModel) {
    val imageUrl = viewModel.imageUrlFromShotDescription()
    if (imageUrl != null) {
        GlideImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier.height(300.dp).testTag(TestTags.TAG_ACTUAL_IMAGE),
        )
    }
    else {
        Image(imageVector = ImageVector.vectorResource(id = R.drawable.default_image_shot),
            contentDescription = "Placeholder image for shot",
            modifier = Modifier.height(300.dp).testTag(TestTags.TAG_PLACEHOLDER_IMAGE), )
    }
}