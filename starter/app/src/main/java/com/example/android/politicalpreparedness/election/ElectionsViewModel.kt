package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionsRepository
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.LOADING
import com.example.android.politicalpreparedness.utils.SUCCESS
import com.example.android.politicalpreparedness.utils.ERROR
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val electionsRepository: ElectionsRepository) : ViewModel() {

    //TODO: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    //TODO: Create live data val for saved elections
    private val _savedElections = MutableLiveData<LiveData<List<Election>>>()
    val savedElections: MutableLiveData<LiveData<List<Election>>>
        get() = _savedElections

    //Status of the request
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status


    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    // This function uses Api service to get the list of upcoming elections
    private fun getUpcomingElections() {
        _status.value = LOADING

        viewModelScope.launch {

            try {
                electionsRepository.getElections()
                val list = electionsRepository.allElections
                _upcomingElections.value = list.toList()
                Log.d("listData", list.toString())
                _status.value = SUCCESS
            } catch (ex: Exception) {

                Log.d("listError", ex.message ?: "undefined")
                _status.value = ERROR
            }

        }
    }

    // This function uses Api service to get the list of saved elections
    private fun getSavedElections() {
        _status.value = LOADING

        viewModelScope.launch {

            try {
              val list = electionsRepository.savedElections
                _savedElections.value = list
                _status.value = SUCCESS
            } catch (ex: Exception) {
                Log.d("listError", ex.message ?: "undefined")
                _status.value = ERROR
            }
        }
    }



    //TODO: Create functions to navigate to saved or upcoming election voter info

    //initialize
    init {
        getUpcomingElections()
        getSavedElections()
    }

}


