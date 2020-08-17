package com.app.masterproject.ui.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.app.masterproject.R
import com.app.masterproject.base.BaseActivity
import com.app.masterproject.databinding.ActivityWebviewBinding
import com.app.masterproject.utils.Constants

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
            binding.toolbarGetInTouch.tvTitle.setText(title)
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
        binding.webview.loadUrl(link)
        showProgressDialog()


    }

    override fun onClick(p0: View?) {
        onBackPressed()
    }
}