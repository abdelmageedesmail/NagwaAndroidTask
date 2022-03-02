package com.abdelmageed.nagwaandroidtask.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(val homeRepositry: HomeRepositry) : ViewModel() {

    private val _fileStateFlow = MutableStateFlow<HomeStateView>(HomeStateView.empty)
    val stateView = _fileStateFlow

    fun getListData() {
        viewModelScope.launch {
            homeRepositry.getListFiles().collect {
                _fileStateFlow.value = HomeStateView.Response(it)
            }
        }
    }

    fun download(url: String) {
        viewModelScope.launch {
            val directory = url.substringBeforeLast("/")
            val fullName = url.substringAfterLast("/")
            val fileName = fullName.substringBeforeLast(".")
            val extension = fullName.substringAfterLast(".")
            homeRepositry.download(url, fileName, extension).collect {
                _fileStateFlow.value = HomeStateView.Download(it)
            }
        }
    }

    fun getAllDownloadedFiles() {
        viewModelScope.launch {
            homeRepositry.getAllFiles().collect {
                _fileStateFlow.value = HomeStateView.GetAllFiles(it)
            }
        }
    }
}