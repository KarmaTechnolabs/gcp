package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gcptrack.api.requestmodel.ChangePasswordRequestModel
import com.gcptrack.api.requestmodel.OrderListRequestModel
import com.gcptrack.api.requestmodel.OrderStatusRequestModel
import com.gcptrack.api.responsemodel.*
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.ChangePasswordRepository
import com.gcptrack.repository.DashBoardRepository

class DashBoardViewModel : ViewModel() {

    private var repository: DashBoardRepository = DashBoardRepository.getInstance()
    private val orderListRequestLiveData = MutableLiveData<OrderListRequestModel>()
    private val customerListRequestLiveData = MutableLiveData<OrderListRequestModel>()
    private val orderStatusRequestLiveData = MutableLiveData<OrderStatusRequestModel>()
    private val changePasswordRequestLiveData = MutableLiveData<ChangePasswordRequestModel>()

    var orderStagesArray = mutableListOf<OrderStatusResponse.OrderStatus>()
    var orderStatusArray = mutableListOf<OrderStatusResponse.OrderStatus>()
    var selectedOrderStatusFilter : String = ""
    var selectedOrderStagesFilter : String = ""

    val orderListResponse: LiveData<Event<APIResource<List<OrdersResponse>>>> =
        orderListRequestLiveData.switchMap {
            repository.callOrderListAPI(it)
        }

    val customerListResponse: LiveData<Event<APIResource<List<CustomersResponse>>>> =
        customerListRequestLiveData.switchMap {
            repository.callCustomerListAPI(it)
        }

    val orderStatusListResponse: LiveData<Event<APIResource<OrderStatusResponse>>> =
        orderStatusRequestLiveData.switchMap {
            repository.callOrderStatusAPI(it)
        }

    fun getOrderStagListAPI(): LiveData<Event<APIResource<List<OrderStatusResponse.OrderStatus>>>> {
        return repository.callOrderStageListAPI()
    }

    //    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()
    fun callOrderListAPI(request: OrderListRequestModel) {
        orderListRequestLiveData.value = request
    }

    fun callCustomerListAPI(request: OrderListRequestModel) {
        customerListRequestLiveData.value = request
    }

    fun callOrderStatusAPI(request: OrderStatusRequestModel) {
        orderStatusRequestLiveData.value = request
    }

    val changePasswordResponse: LiveData<Event<APIResource<EmptyResponse>>> =
        changePasswordRequestLiveData.switchMap {
            ChangePasswordRepository.getInstance().callChangePasswordAPI(it)
        }

    fun callChangePasswordAPI(changePasswordRequestModel: ChangePasswordRequestModel) {
        changePasswordRequestLiveData.value = changePasswordRequestModel
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
