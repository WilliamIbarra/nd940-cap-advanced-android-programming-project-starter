package com.example.android.politicalpreparedness.database


interface ElectionsDataSource {

    suspend fun saveElection(election: ElectionDB)

    fun getElections(): Result<List<ElectionDB>>

    suspend fun getElectionById(electionId: Int): Result<ElectionDB?>

    suspend fun deleteElection(electionId: Int)

    suspend fun deleteElections()
}