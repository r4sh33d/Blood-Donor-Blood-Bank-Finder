package com.efedaniel.bloodfinder.bloodfinder.models.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UploadBloodAvailabilityRequest(
    val bloodType: String,
    val billingType: String,
    val donorID: String,
    val donorPhoneNumber: String,
    val donorName: String,
    val donorReligion: String,
    val creationTime: String = System.currentTimeMillis().toString(),
    val bloodAvailabilityID: String? = null,
    val status: String = "Pending"
) : Parcelable