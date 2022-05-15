package com.app.gcp.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.work.*
import com.app.gcp.R
import com.app.gcp.adapter.OrderDetailAdditionalDetailsAdapter
import com.app.gcp.adapter.OrderDetailProductAdapter
import com.app.gcp.api.requestmodel.OrderDetailsRequestModel
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.BaseActivity
import com.app.gcp.custom.showToast
import com.app.gcp.databinding.ActivityOrderDetailBinding
import com.app.gcp.utils.Constants
import com.app.gcp.utils.FileDownloadWorker
import com.app.gcp.utils.UserStateManager
import com.app.gcp.viewmodel.OrderDetailViewModel

class OrderDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOrderDetailBinding
    val viewModel: OrderDetailViewModel by viewModels()
    private val productListArray = mutableListOf<OrdersDetailsResponse.Product>()
    private var productListAdapter: OrderDetailProductAdapter? = null
    private val additionalDetailsListArray = mutableListOf<OrdersDetailsResponse.CustomField>()
    private var additionalDetailsListAdapter: OrderDetailAdditionalDetailsAdapter? = null
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        initview()

        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && !checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                201
            )
        }
    }

    private fun initview() {
        binding.lifecycleOwner = this
        binding.clickListener = this
        binding.model = viewModel
        binding.toolbarOrderStatus.ivBack.setOnClickListener(this)
        binding.toolbarOrderStatus.ivMedia.setOnClickListener(this)
        binding.toolbarOrderStatus.tvTitle.text = getString(R.string.order_detail)
        binding.toolbarOrderStatus.ivMedia.visibility = View.VISIBLE
        binding.toolbarOrderStatus.ivMedia.setImageResource(R.drawable.ic_download)
        workManager = WorkManager.getInstance(this)

        productListAdapter = OrderDetailProductAdapter(this)
//        productListAdapter?.setClickListener(this)
        binding.rvProduct.adapter = productListAdapter
        additionalDetailsListAdapter = OrderDetailAdditionalDetailsAdapter(this)
//        productListAdapter?.setClickListener(this)
        binding.rvAdditional.adapter = additionalDetailsListAdapter

        if (intent.extras != null && intent.hasExtra(Constants.EXTRA_ORDER_STATUS)) {
            viewModel.orderStatusResponse.value =
                intent?.getParcelableExtra<OrdersResponse>(Constants.EXTRA_ORDER_STATUS)
            callOrderDetailApi()
        }

        viewModel.orderDetailsResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                manageAPIResource(
                    response, isShowProgress = false,
                    successListener = object : (OrdersDetailsResponse, String) -> Unit {
                        override fun invoke(it: OrdersDetailsResponse, message: String) {
//                            showToast(message)
                            viewModel.orderDetailResponseLiveData.postValue(it)
                            productListArray.clear()
                            it.products?.let { it1 -> productListArray.addAll(it1) }
                            productListAdapter?.setItems(productListArray as ArrayList<OrdersDetailsResponse.Product?>)
                            additionalDetailsListArray.clear()
                            it.customFields?.let { it1 -> additionalDetailsListArray.addAll(it1) }
                            additionalDetailsListAdapter?.setItems(additionalDetailsListArray as ArrayList<OrdersDetailsResponse.CustomField?>)
                            binding.tvSubtotalValue.text =
                                "₹ ".plus(productListArray.sumBy { Integer.parseInt(it.total) }
                                    .toString())

                            binding.tvGstValue.text =
                                "₹ ".plus(productListArray.sumBy { Integer.parseInt(it.total) }
                                    .toString())
                            binding.tvGrandTotalValue.text =
                                "₹ ".plus(productListArray.sumBy { Integer.parseInt(it.total) }
                                    .toString())

                            binding.tvNoData.visibility = View.GONE
                        }
                    },
                    failureListener = object : () -> Unit {
                        override fun invoke() {
                            binding.tvNoData.visibility = View.VISIBLE
                        }
                    })
            }
        }
    }


    private fun callOrderDetailApi() {
        viewModel.callOrderDetailAPI(
            OrderDetailsRequestModel(
                user_type = UserStateManager.getUserProfile()?.user_type.toString(),
                orderId = viewModel.orderStatusResponse.value?.orderId,
                token =
                UserStateManager.getBearerToken()
            )
        )
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.toolbarOrderStatus.ivBack -> onBackPressed()
            binding.tvPoDownload -> startDownloadingFile(
                "GCP_" + System.currentTimeMillis() + ".pdf",
                viewModel.orderDetailResponseLiveData.value?.details?.files
            )
            binding.toolbarOrderStatus.ivMedia -> startDownloadingFile(
                "GCP_" + System.currentTimeMillis() + ".pdf",
                viewModel.orderDetailResponseLiveData.value?.pdf
            )
//                Utils.downloadPdf(this,viewModel.orderDetailResponseLiveData.value?.details?.files,"GCP"+System.currentTimeMillis()+".pdf")
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startDownloadingFile(fileName: String, fileUrl: String?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder()

        data.apply {
            putString(Constants.KEY_FILE_NAME, fileName)
            putString(Constants.KEY_FILE_URL, fileUrl)
            putString(Constants.KEY_FILE_TYPE, "PDF")
        }

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val uri = it.outputData.getString(Constants.KEY_FILE_URI)
                            uri?.let {
//                                btnOpenFile.text = "Open File"
//                                btnOpenFile.visibility = View.VISIBLE
//                                btnOpenFile.setOnClickListener {
//                                    val intent = Intent(Intent.ACTION_VIEW)
//                                    intent.setDataAndType(uri.toUri(), "application/pdf")
//                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                                    try {
//                                        startActivity(intent)
//                                    } catch (e: ActivityNotFoundException) {
//                                        Toast.makeText(
//                                            this@MainActivity,
//                                            "Can't open Pdf",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
                            }
                        }
                        WorkInfo.State.FAILED -> {
                            showToast("Download in failed")
//                            btnOpenFile.text = "Download in failed"
                        }
                        WorkInfo.State.RUNNING -> {
                            showToast("Download in progress..")
//                            btnOpenFile.text = "Download in progress.."
                        }
                        else -> {

                        }
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 201) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                //openCamera()

            } else {
                showToast("Permission denied")
            }
        }
    }
}