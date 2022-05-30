package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.asDatabaseModel
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class ElectionsRepository(private val electionDatabase: ElectionDatabase) {

    // Saved Elections
    val savedElections: LiveData<List<Election>> = Transformations.map(electionDatabase.electionDao.getElections()) {
        it.asDomainModel()
    }

    // All elections
    val allElections: ArrayList<Election> = ArrayList()

    // Upcoming Elections


    // Api call elections
    suspend fun getElections() {
            withContext(Dispatchers.IO) {
                val elections = CivicsApi.retrofitService.getElections()

                //val allElections: ArrayList<Election> = ArrayList()
                elections.elections.forEach { election ->
                    allElections.add(election)
                }

                //electionDatabase.electionDao.saveElections(allElections.asDatabaseModel().toList())
            }


//    suspend fun getElections() {
//        withContext(Dispatchers.IO){
//            val elections = CivicsApi.retrofitService.getElections()
//
//            val allElections: ArrayList<Election> = ArrayList()
//            elections.elections.forEach { election ->
//                    allElections.add(election)
//            }
//
//            electionDatabase.electionDao.saveElections(allElections.asDatabaseModel().toList())
//        }
    }
}
