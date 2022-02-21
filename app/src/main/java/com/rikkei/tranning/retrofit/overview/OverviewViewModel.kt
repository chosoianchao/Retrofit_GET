package com.rikkei.tranning.retrofit.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikkei.tranning.retrofit.network.MarsApis
import com.rikkei.tranning.retrofit.network.MarsPhoto
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<MarsApisStatus>()
    val status: LiveData<MarsApisStatus> = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos

    init {
        getMarsPhoto()
    }

    private fun getMarsPhoto() {
        viewModelScope.launch {
            _status.value = MarsApisStatus.LOADING
            try {
                _photos.value = MarsApis.retrofitService.getPhotos()
                _status.value = MarsApisStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApisStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}

enum class MarsApisStatus {
    LOADING, ERROR, DONE
}
