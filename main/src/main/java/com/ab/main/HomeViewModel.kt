package com.ab.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : ViewModel() {

  val state = MutableLiveData<String>()

  fun load() {
    viewModelScope.launch {


    }
  }
}
