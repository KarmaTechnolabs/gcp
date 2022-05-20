package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.CustomerDetailsRequestModel
import com.app.gcp.api.responsemodel.CustomerDetailsResponse
import com.app.gcp.api.responsemodel.CustomersResponse
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.CustomerDetailsRepository

class CustomerDetailViewModel : ViewModel() {

    private var repository: CustomerDetailsRepository = CustomerDetailsRepository.getInstance()
    private val customerDetailRequestLiveData = MutableLiveData<CustomerDetailsRequestModel>()
    val customerDetailResponseLiveData = MutableLiveData<CustomerDetailsResponse.Client>()
    val customerResponse = MutableLiveData<CustomersResponse>()

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
