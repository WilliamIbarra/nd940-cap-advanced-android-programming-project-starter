package com.example.android.politicalpreparedness.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.politicalpreparedness.database.Converters
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.squareup.moshi.Json

@Entity
data class ElectionDB constructor(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: String?,
    @Embedded(prefix = "division_") @Json(name = "ocdDivisionIdo") val division: Division?,
    @ColumnInfo(name = "newDivision") @Json(name = "ocdDivisionId") val newDivision: String
)

fun List<ElectionDB>.asDomainModel(): List<Election> {

    val converters = Converters()
    return map {
        Election(
            id = it.id,
            name = it.name,
            electionDay = it.electionDay,
            division = ElectionAdapter().divisionFromJson(it.newDivision),
            newDivision = it.newDivision
        )
    }
}
