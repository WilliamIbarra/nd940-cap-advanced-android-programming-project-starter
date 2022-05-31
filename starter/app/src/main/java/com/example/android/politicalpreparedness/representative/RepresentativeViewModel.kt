package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionsRepository
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.utils.LOADING
import com.example.android.politicalpreparedness.utils.SUCCESS
import com.example.android.politicalpreparedness.utils.ERROR
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.utils.LOADING
import kotlinx.coroutines.launch

class RepresentativeViewModel(private val electionsRepository: ElectionsRepository): ViewModel() {

    //TODO: Establish live data for representatives and address

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _state = MutableLiveData<String>()
    val state: LiveData<String>
        get() = _state

    val addressLine1 = MutableLiveData<String>()
    val addressLine2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val zip = MutableLiveData<String>()

    private val includeOffices = true

    //Status of the request
    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    //TODO: Create function to fetch representatives from API from a provided address
    fun representatives() {
        _status.value = LOADING

        getInputAddress()
        viewModelScope.launch {
            try {
           val (offices, officials) = CivicsApi.retrofitService.getRepresentatives(address.value ?: "", includeOffices)
            _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                Log.v("Result", "##### RESULT REPRESENTATIVES ######")
                Log.v("Result", _representatives.value.toString())
                _status.value = SUCCESS
            }catch (ex: Exception) {
                Log.e("Result", "##### RESULT REPRESENTATIVES ######")
                Log.e("Result", ex.toString())
                _status.value = ERROR
            }
        }
    }

    fun saveState(state: String) {
        _state.value = state
        Log.e("Result", "##### STATE ######")
        Log.e("Result", state)
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
     fun geoLocation(address: Address) {
        addressLine1.postValue(address.line1)
        addressLine2.postValue(address.line2.toString())
        zip.postValue(address.zip)
        city.postValue(address.city)

        representatives()

    }

    //TODO: Create function to get address from individual fields
    private fun getInputAddress() {
        _address.value = addressLine1.value + " " + addressLine2.value + " " + zip.value + " " + city.value + " " + state.value
        Log.e("Result", "##### ADDRESS ######")
        Log.e("Result", address.value.toString())
    }




}
