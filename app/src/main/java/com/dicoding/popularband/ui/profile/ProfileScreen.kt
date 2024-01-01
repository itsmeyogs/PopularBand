package com.dicoding.popularband.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.popularband.R
import com.dicoding.popularband.ViewModelFactory
import com.dicoding.popularband.data.model.Profile
import com.dicoding.popularband.di.Injection
import com.dicoding.popularband.ui.state.UiState
import com.dicoding.popularband.ui.theme.PopularBandTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState->
        when(uiState){
            is UiState.Loading->{
                viewModel.getProfile()
            }
            is UiState.Success->{
                ProfileContent(
                    profile = uiState.data,
                    modifier = Modifier
                )

            }
            is UiState.Error->{
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT ).show()
            }
        }
    }

}

@Composable
fun ProfileContent(
    profile: Profile,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = profile.photoUrl),
                contentDescription = stringResource(R.string.profil_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
            )
            Text(
                text= profile.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = profile.email,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic
                )
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview(){
    PopularBandTheme {
        ProfileContent(profile = Profile(photoUrl = R.drawable.me, name = "Ariq", "ariqdzulfikar27@gmail.com"))
    }
}