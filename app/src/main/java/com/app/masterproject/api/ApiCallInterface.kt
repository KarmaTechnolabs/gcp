package com.app.masterproject.api

import com.app.masterproject.api.requestmodel.ChangePasswordRequestModel
import com.app.masterproject.api.requestmodel.ForgotPasswordRequestModel
import com.app.masterproject.api.requestmodel.LoginRequestModel
import com.app.masterproject.api.requestmodel.RegisterRequestModel
import com.app.masterproject.api.responsemodel.LoginResponse
import com.app.masterproject.api.responsemodel.RegisterResponse
import com.app.masterproject.base.BaseRequestModel
import com.app.masterproject.base.BaseResponseModel
import com.app.masterproject.model.*
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