package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDB
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add live data to hold voter info
    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    //TODO: Elections status
    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean>
        get() = _saved

    //TODO: Add var and methods to populate voter info
    fun setElection(election: Election) {
        _election.value = election
        isSaved()
    }

    //TODO: Add var and methods to support loading URLs
    val stateLocations = "https://myvote.wi.gov/en-us/"
    val stateBallot = "https://myvote.wi.gov/en-us/"

    //TODO: Add var and methods to save and remove elections to local database
    fun saveElection() {

        viewModelScope.launch {

            dataSource.saveElection(
                ElectionDB(
                    id = _election.value?.id ?: -1,
                    name = _election.value?.name ?: "",
                    electionDay = _election.value?.electionDay ?: "",
                    division = _election.value?.division,
                    newDivision = _election.value?.newDivision ?: ""
                )
            )
        }


    }

    fun deleteElection() {
        viewModelScope.launch {
            dataSource.deleteElection(_election.value?.id ?: -1)
        }
    }
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    private fun isSaved() {
        viewModelScope.launch {
            val election = dataSource.getElectionById(_election.value?.id ?: -1)

            _saved.value = election != null

        }

    }

    fun saveOrDeleteElection() {
       if (_saved.value == false) {
           saveElection()
           Log.v("Saved", "Election saved!!")
       } else {
           deleteElection()
           Log.v("Deleted", "Election deleted!!")
       }
    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
}