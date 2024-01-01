package com.dicoding.popularband.ui.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.popularband.ViewModelFactory
import com.dicoding.popularband.data.model.Band
import com.dicoding.popularband.di.Injection
import com.dicoding.popularband.ui.state.UiState
import com.dicoding.popularband.ui.theme.PopularBandTheme

@Composable
fun DetailScreen(
    id:Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBandById(id)
            }
            is UiState.Success -> {
                DetailContent(detailBand = uiState.data)
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT ).show()
            }
        }
    }
}

@Composable
fun DetailContent(
    detailBand:Band,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            AsyncImage(
                model = detailBand.photoUrl,
                contentDescription = "Gambar "+detailBand.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)

            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = detailBand.name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(12.dp)
                )
                Divider()
                Text(
                    text = detailBand.desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    PopularBandTheme {
        DetailContent(
            Band(
                id = 1,
                photoUrl = "",
                name = "Slipknot",
                desc = "Slipknot adalah sebuah band metal asal Amerika yang terkenal dengan gaya musiknya yang keras, energetik, dan kontroversial. Dibentuk pada tahun 1995 di Iowa, Slipknot dikenal dengan anggota-anggota mereka yang mengenakan topeng dan seragam unik, menciptakan citra yang misterius dan menyeramkan. Dengan kombinasi brutalitas metal, rap, dan sentuhan industrial, musik mereka seringkali dianggap sebagai ekspresi pemberontakan dan kemarahan. Album-album seperti \"Slipknot\" dan \"Iowa\" membuktikan popularitas mereka dalam komunitas metal, sementara performa panggung mereka yang intens dan penuh energi telah memenangkan penggemar di seluruh dunia. Slipknot tidak hanya menjadi ikon dalam genre metal, tetapi juga meraih kesuksesan komersial yang signifikan, menjadikan mereka salah satu band metal paling berpengaruh dan terkenal dalam sejarah musik.")

        )
    }
}