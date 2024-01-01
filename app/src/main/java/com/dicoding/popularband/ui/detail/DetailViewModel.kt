package com.dicoding.popularband.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.popularband.data.model.Band
import com.dicoding.popularband.data.repository.Repository
import com.dicoding.popularband.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Band>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Band>>
        get() = _uiState

    fun getBandById(id:Long) {
        viewModelScope.launch {
            repository.getBandById(id)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it)
                }

        }
    }
}