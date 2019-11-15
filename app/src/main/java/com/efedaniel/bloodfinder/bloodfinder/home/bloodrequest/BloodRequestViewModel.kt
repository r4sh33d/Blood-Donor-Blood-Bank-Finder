package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest

import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class BloodRequestViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
): BaseViewModel() {

    private val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    val donorList = mutableListOf<UploadBloodAvailabilityRequest>()
    private var numOfRequests = 0

    fun getCompatibleBloods(bloodType: String, billingType: String) {
        _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.searching))
        donorList.clear()
        numOfRequests = Data.bloodCompatibilityMapping.getValue(bloodType).size
        for (type in Data.bloodCompatibilityMapping.getValue(bloodType)) {
            getFilteredBloodAvailability(type)
        }
    }

    private fun getFilteredBloodAvailability(type: String) {
        viewModelScope.launch {
            val response = databaseRepository.getFilteredBloodAvailability(ApiKeys.BLOOD_TYPE, type)
            if (response?.isSuccessful == true) {
                val bloodPostings = GsonUtils.fromJson<HashMap<String, UploadBloodAvailabilityRequest>>(response.body())
                donorList.addAll(ArrayList(bloodPostings.values))
                Timber.d(donorList.size.toString())
                numOfRequests--
                if (numOfRequests == 0) {
                    triggerNextStep()
                }
            } else {
                numOfRequests--
                if (numOfRequests == 0) {
                    triggerNextStep()
                }
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun triggerNextStep() {
        //TODO Come and filter the result using the algorithm.
        Timber.d(donorList.size.toString())
        _loadingStatus.value = LoadingStatus.Success
    }

    override fun addAllLiveDataToObservablesList() {

    }

}