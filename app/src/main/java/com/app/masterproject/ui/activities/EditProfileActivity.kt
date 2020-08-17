package com.app.masterproject.ui.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.app.masterproject.BuildConfig
import com.app.masterproject.R
import com.app.masterproject.api.responsemodel.LoginResponse
import com.app.masterproject.base.APIResource
import com.app.masterproject.base.BaseActivity
import com.app.masterproject.custom.Event
import com.app.masterproject.custom.loadImage
import com.app.masterproject.custom.loadImageFromUrl
import com.app.masterproject.custom.showToast
import com.app.masterproject.databinding.ActivityEditProfileBinding
import com.app.masterproject.listeners.OnItemClickListener
import com.app.masterproject.listeners.PermissionListener
import com.app.masterproject.listeners.SnackbarListener
import com.app.masterproject.model.StateGenericModel
import com.app.masterproject.model.StateModel
import com.app.masterproject.model.StateRequestModel
import com.app.masterproject.model.UpdateProfileRequestModel
import com.app.masterproject.ui.dialogs.SelectMediaBottomSheetDialog
import com.app.masterproject.utils.Constants
import com.app.masterproject.utils.DateTimeUtils
import com.app.masterproject.utils.UserStateManager
import com.app.masterproject.utils.Utils
import com.app.masterproject.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*

class EditProfileActivity : BaseActivity(), View.OnClickListener, OnItemClickListener<Int> {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var myCalendar: Calendar
    private var currentPhotoPath: String? = null
    private val profileViewModel by viewModels<ProfileViewModel>()
    private var stateList: ArrayList<StateGenericModel>? = null
    private var districtList: ArrayList<StateGenericModel>? = null
    private var isNoStateData = false
    private var stateId:Int? = null
    private var cityId:Int? = null
    private var loginResponseModel: LoginResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        initView()
    }

    private fun initView() {
        binding.clickListener = this
        binding.toolbarProfile.tvTitle.setText(getString(R.string.edit_profile))
        binding.toolbarProfile.ivBack.setOnClickListener(this)
        myCalendar = Calendar.getInstance()

        loginResponseModel = UserStateManager.getUserProfile()
        binding.model = loginResponseModel
        profileViewModel.stateResponse.observe(this, ::handleStateResponse)
        profileViewModel.updateProfileResponse.observe(this, ::handleUpdateProfileResponse)

        stateId = loginResponseModel?.stateData?.stateId
        cityId = loginResponseModel?.cityData?.cityId
    }

    private fun handleStateResponse(event: Event<APIResource<List<StateModel>>>?) {
        event?.getContentIfNotHandled()?.let { response ->
            manageAPIResource(response) { it, message ->

                if (!it.isNullOrEmpty()) {
                    if (stateList == null)
                        stateList = ArrayList()
                    for (state in it)
                        stateList?.add(StateGenericModel(state.stateId, state.name))
                }
                showStatesListSelection()
            }
        }
    }


    private fun handleUpdateProfileResponse(event: Event<APIResource<LoginResponse>>?) {
        event?.getContentIfNotHandled()?.let { response ->
            manageAPIResource(response) { it, message ->

                it.token = loginResponseModel?.token!!
                it.deviceToken = loginResponseModel?.deviceToken!!
                it.deviceType = loginResponseModel?.deviceType!!
            }
        }
    }

    private fun showStatesListSelection() {
        if (!stateList.isNullOrEmpty()) {
            val intent = Intent(this, StatePickerActivity::class.java)
            intent.putParcelableArrayListExtra(StatePickerActivity.OPTIONS_DATA, stateList)
            intent.putExtra(StatePickerActivity.OPTIONS_TITLE, getString(R.string.select_state))
            startActivityForResult(intent, Constants.REQUESTCODE_SELECT_STATE)
        } else getStateData()
    }

    private fun showDistrictPicker() {
        if (binding.tiState.editText?.text.toString().isEmpty()) {
            showToast(getString(R.string.please_select_state))
            return
        }
        if (!districtList.isNullOrEmpty()) {
            isNoStateData = false
            val intent = Intent(this, StatePickerActivity::class.java)
            intent.putParcelableArrayListExtra(StatePickerActivity.OPTIONS_DATA, districtList)
            intent.putExtra(StatePickerActivity.OPTIONS_TITLE, getString(R.string.select_district))
            startActivityForResult(intent, Constants.REQUESTCODE_SELECT_CITY)
        } else {
            isNoStateData = true
        }
    }

    private fun getStateData() {
        val requestModel = StateRequestModel()
        profileViewModel.getStateLists(requestModel)
    }


    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, dateListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    internal var dateListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val date = DateTimeUtils.ddMMMYY.format(myCalendar.time)
            binding.tiDob.editText?.setText(date)
        }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbarProfile.ivBack -> onBackPressed()
            binding.tiDob, binding.tiDob.editText -> openDatePickerDialog()
            binding.ivEditProfilePic -> {
                if (!Utils.isCameraAndGalleryPermissionGranted(this)) {
                    showPermissionAlert(
                        getString(R.string.this_app_requires_camera_permission_to_continue),
                        permissionListener
                    )
                } else
                    showImagePickerBottomDialog()
            }
            binding.tiState, binding.tiState.editText -> showStatesListSelection()
//            binding.tiDistrict, binding.tiDistrict.editText -> showDistrictPicker()
            binding.btnSave -> handleValidationsAndUpdateProfile()
        }
    }

    // listener for permission dialog click handle
    val permissionListener = object : PermissionListener {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPermissionClick() {
            if (!Utils.isCameraAndGalleryPermissionGranted(this@EditProfileActivity)) {
                requestPermissions(cameraPermissions, Constants.PICK_IMAGE_REQUESTCODE)
            } else
                showImagePickerBottomDialog()
        }
    }

    private fun showImagePickerBottomDialog() {
        val dialog = SelectMediaBottomSheetDialog()
        dialog.setImageChooseListener(this)
        dialog.show(supportFragmentManager, "")
    }

    override fun onItemClick(view: View?, obj: Int, position: Int) {
        if (obj == Constants.PICK_IMAGE_FROM_CAMERA)
            openCamera()
        else openGallery()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            val photoFile: File? = createImageFile()

            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri =
                    FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID.plus(".fileprovider"),
                        it
                    )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, Constants.PICK_IMAGE_FROM_CAMERA)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, Constants.PICK_IMAGE_FROM_GALLERY)
    }

    /**
     * creates image file
     * @return image file
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name

        val storageDir: File =
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${Calendar.getInstance().timeInMillis}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.PICK_IMAGE_REQUESTCODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                )
                    showImagePickerBottomDialog()
                else showSnackbar(binding.container,
                    getString(R.string.allow_permission_to_use_this_feature),
                    Snackbar.LENGTH_LONG,
                    object : SnackbarListener {
                        override fun onSnackbarClick() {
                            val intent = Intent(
                                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                , Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                            )
                            startActivity(intent)
                        }
                    })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.PICK_IMAGE_FROM_GALLERY -> {
                if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {

                    loadImageFromUrl(binding.civProfilePic, data.data)
                    currentPhotoPath =
                        Utils.getFilePath(this, data.data!!)
                }
            }
            Constants.PICK_IMAGE_FROM_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    loadImage(binding.civProfilePic, currentPhotoPath)
                }
            }
            Constants.REQUESTCODE_SELECT_STATE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val model =
                        data?.getParcelableExtra<Parcelable>(StatePickerActivity.OPTIONS_DATA) as StateGenericModel
                    binding.tiState.editText?.setText(model.title)
                    stateId = model.id
                    cityId = null
//                    binding.tiDistrict.editText?.setText("")
                }
            }
            Constants.REQUESTCODE_SELECT_CITY -> {
                if (resultCode == Activity.RESULT_OK) {
                    val model =
                        data?.getParcelableExtra<Parcelable>(StatePickerActivity.OPTIONS_DATA) as StateGenericModel
//                    binding.tiDistrict.editText?.setText(model.title)
                    cityId = model.id
                }
            }
        }
    }


    private fun handleValidationsAndUpdateProfile() {
        binding.tiFirstName.error = null
        binding.tiLastName.error = null
        binding.tiState.error = null
        binding.tiDob.error = null

        val requestModel = UpdateProfileRequestModel(firstName = binding.tiFirstName.editText?.text.toString().trim()
            ,lastName = binding.tiLastName.editText?.text.toString().trim()
        ,dateOfBirth = binding.tiDob.editText?.text.toString().trim()
        ,userMobile = binding.tiMobileNumber.editText?.text.toString().trim()
        ,stateId = stateId,cityId = cityId)

        if (requestModel.firstName.isNullOrEmpty()){
            binding.tiFirstName.error = getString(R.string.enter_valid_first_name)
            binding.scroll.smoothScrollTo(0,binding.scroll.top)
        } else if (requestModel.lastName.isNullOrEmpty()){
            binding.tiLastName.error = getString(R.string.validation_last_name)
            binding.scroll.smoothScrollTo(0,binding.scroll.top)
        } else if (requestModel.stateId == null){
            binding.scroll.smoothScrollTo(0,700)
            binding.tiState.error = getString(R.string.validation_empty_state)
        } else if (requestModel.dateOfBirth.isNullOrEmpty())
            binding.tiDob.error = getString(R.string.validation_empty_dob)
        else {

            lifecycleScope.launch {
                if (!TextUtils.isEmpty(currentPhotoPath)) {
                    val compressedDocumentPath =
                            Compressor.compress(this@EditProfileActivity, File(currentPhotoPath), Dispatchers.Main) {
                                quality(60) }

                    requestModel.profilePhoto = compressedDocumentPath.absolutePath

                }
            }
        }
    }
}