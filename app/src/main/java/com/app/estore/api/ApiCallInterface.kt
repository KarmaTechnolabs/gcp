package com.app.estore.api

import com.app.estore.api.requestmodel.ChangePasswordRequestModel
import com.app.estore.api.requestmodel.ForgotPasswordRequestModel
import com.app.estore.api.requestmodel.LoginRequestModel
import com.app.estore.api.requestmodel.RegisterRequestModel
import com.app.estore.api.responsemodel.LoginResponse
import com.app.estore.api.responsemodel.RegisterResponse
import com.app.estore.base.BaseRequestModel
import com.app.estore.base.BaseResponseModel
import com.app.estore.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiCallInterface {

    @POST(APIConstants.API_REGISTER)
    fun callRegisterAPI(@Body requestBody: BaseRequestModel<RegisterRequestModel>): Single<BaseResponseModel<RegisterResponse>>

    @POST(APIConstants.API_SIGN_IN)
    fun callLoginAPI(@Body requestBody: BaseRequestModel<LoginRequestModel>): Single<BaseResponseModel<LoginResponse>>

    @POST(APIConstants.API_FORGOT_PASSWORD)
    fun callForgotPasswordAPI(@Body requestBody: BaseRequestModel<ForgotPasswordRequestModel>): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_LOGOUT)
    fun callLogoutAPI(): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_CHANGE_PASSWORD)
    fun callChangePasswordAPI(@Body requestBody: BaseRequestModel<ChangePasswordRequestModel>): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_GET_STATE)
    fun getStateList(@Body requestBody: BaseRequestModel<StateRequestModel>): Single<BaseResponseModel<List<StateModel>>>

    @Multipart
    @POST(APIConstants.API_UPDATE_PROFILE)
    fun updateProfile(@Part profile_photo: MultipartBody.Part?, @Part("data") data: RequestBody)
            : Single<BaseResponseModel<LoginResponse>>

}