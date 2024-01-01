package com.dicoding.popularband.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.popularband.ViewModelFactory
import com.dicoding.popularband.data.model.Band
import com.dicoding.popularband.di.Injection
import com.dicoding.popularband.ui.component.BandItem
import com.dicoding.popularband.ui.state.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel : HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,

    ){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState->
        when(uiState){
            is UiState.Loading->{
                viewModel.getAllBand()
            }
            is UiState.Success->{
                HomeContent(
                    listBand = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )


            }
            is UiState.Error->{
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT ).show()
            }
        }
    }
}

@Composable
fun HomeContent(
    listBand: List<Band>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(listBand, key = {it.id}){
            BandItem(photoUrl = it.photoUrl, name = it.name, modifier.clickable {
                navigateToDetail(it.id)
            })
        }
    }
}