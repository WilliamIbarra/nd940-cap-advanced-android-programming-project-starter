package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Division(
        val id: String,
        val country: String,
        val state: String
) : Parcelable