package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ElectionResponse(
        val kind: String?,
        val elections: List<Election>
): Parcelable