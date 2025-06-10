package com.zavedahmad.randomduck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zavedahmad.randomduck.api.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuckViewModel  @Inject constructor(): ViewModel() {
    private val duckApi = RetrofitInstance.duckApi
    private val _link = MutableStateFlow("No Link")
    val link: StateFlow<String> = _link.asStateFlow()


    fun getLinkToImageRandom() {
        viewModelScope.launch {
            val response = duckApi.getLink()
            if (response.isSuccessful) {
               _link.value = response.body()?.url?: "no url found in response"
            }else{
                _link.value= response.toString()
            }
        }


    }
}