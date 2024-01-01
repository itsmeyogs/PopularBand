package com.dicoding.popularband.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.popularband.data.model.Profile
import com.dicoding.popularband.data.repository.Repository
import com.dicoding.popularband.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Profile>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Profile>>
        get() = _uiState

    fun getProfile(){
        viewModelScope.launch {
            repository.getProfile()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}