package com.gcptrack.ui.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gcptrack.R
import com.gcptrack.base.BaseActivity
import com.gcptrack.databinding.ActivityWebviewBinding

class WebActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding:ActivityWebviewBinding

    private var title:String? = null
    private var link:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_webview)
        initView()
    }

    private fun initView(){
        binding.webview.loadUrl("file:///android_asset/other_services.html")
//        if (intent.extras!=null){
//            title = intent.getStringExtra(Constants.EXTRA_TITLE);
//            link = intent.getStringExtra(Constants.EXTRA_LINK);
//            binding.toolbarGetInTouch.tvTitle.text = title
//        }
//
        binding.toolbarGetInTouch.ivBack.setOnClickListener(this)
//
//        binding.webview.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                view.loadUrl(url)
//                return false
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                super.onPageFinished(view, url)
//                hideProgressDialog()
//            }
//        }
//
        binding.webview.settings.javaScriptEnabled = true
//        link?.let { binding.webview.loadUrl(it) }
//        showProgressDialog()


    }

    override fun onClick(p0: View?) {
        onBackPressed()
    }
}