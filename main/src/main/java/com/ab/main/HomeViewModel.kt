package com.ab.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : ViewModel() {

  fun load() {
    viewModelScope.launch {
    }
  }
}
