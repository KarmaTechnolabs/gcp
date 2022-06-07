package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gcptrack.api.requestmodel.CustomerDetailsRequestModel
import com.gcptrack.api.responsemodel.CustomerDetailsResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.CustomerDetailsRepository

class CustomerDetailViewModel : ViewModel() {

    private var repository: CustomerDetailsRepository = CustomerDetailsRepository.getInstance()
    private val customerDetailRequestLiveData = MutableLiveData<CustomerDetailsRequestModel>()
    val customerDetailResponseLiveData = MutableLiveData<CustomerDetailsResponse.Client>()
    val customerId = MutableLiveData<String>()

    val customerDetailsResponse: LiveData<Event<APIResource<CustomerDetailsResponse>>> =
        customerDetailRequestLiveData.switchMap {
            repository.callCustomerDetailsUpdateAPI(it)
        }

    fun callCustomerDetailAPI(request: CustomerDetailsRequestModel) {
        customerDetailRequestLiveData.value = request
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
