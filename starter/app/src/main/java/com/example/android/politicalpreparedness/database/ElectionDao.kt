package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(election: ElectionDB)

    //TODO: Add select all election query
    @Query("SELECT * FROM electiondb")
    fun getElections(): LiveData<List<ElectionDB>>

    //TODO: Add select single election query
    @Query("SELECT * FROM electiondb WHERE id = :electionId")
    suspend fun getElectionById(electionId: Int): ElectionDB?

    //TODO: Add delete query
    @Query("DELETE FROM electiondb WHERE id = :electionId")
    suspend fun deleteElection(electionId: Int)

    //TODO: Add clear query
    @Query("DELETE FROM electiondb")
    suspend fun deleteElections()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElections(elections: List<ElectionDB>)

}