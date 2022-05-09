package com.app.gcp.ui.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityWebviewBinding
import com.app.gcp.utils.Constants

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

        if (intent.extras!=null){
            title = intent.getStringExtra(Constants.EXTRA_TITLE);
            link = intent.getStringExtra(Constants.EXTRA_LINK);
            binding.toolbarGetInTouch.tvTitle.text = title
        }

        binding.toolbarGetInTouch.ivBack.setOnClickListener(this)

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                hideProgressDialog()
            }
        }

        binding.webview.settings.javaScriptEnabled = true
        link?.let { binding.webview.loadUrl(it) }
        showProgressDialog()


    }

    override fun onClick(p0: View?) {
        onBackPressed()
    }
}