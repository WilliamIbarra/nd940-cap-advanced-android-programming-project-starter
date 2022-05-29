package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import androidx.room.*
import com.example.android.politicalpreparedness.database.Converters
import com.example.android.politicalpreparedness.database.ElectionDB
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "election_table")
@Parcelize
data class Election(
        @PrimaryKey @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "electionDay") val electionDay: String?,
        @Embedded(prefix = "division_") @Json(name = "ocdDivisionIdo") val division: Division?,
        @ColumnInfo(name = "newDivision") @Json(name = "ocdDivisionId") val newDivision: String
) : Parcelable

fun ArrayList<Election>.asDatabaseModel(): Array<ElectionDB> {
        val converters = Converters()
        return this.map {
                ElectionDB(
                     id = it.id,
                     name = it.name,
                     electionDay = it.electionDay,
                     division = it.division,
                    newDivision = it.newDivision

                )
        }.toTypedArray()
}

