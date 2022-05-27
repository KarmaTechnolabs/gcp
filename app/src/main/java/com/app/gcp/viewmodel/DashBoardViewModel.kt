package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.ChangePasswordRequestModel
import com.app.gcp.api.requestmodel.OrderListRequestModel
import com.app.gcp.api.requestmodel.OrderStatusRequestModel
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.*
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.ChangePasswordRepository
import com.app.gcp.repository.DashBoardRepository

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
