package com.app.gcp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.gcp.R
import com.app.gcp.base.BaseActivity
import com.app.gcp.databinding.ActivityOrderDetailBinding
import com.app.gcp.databinding.ActivityOtherServicesBinding

class OtherServicesActivity : BaseActivity() {
    private lateinit var binding: ActivityOtherServicesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_other_services)

        binding.expandTextView.text = "Expandable Text View is an android library that allows the users to create the text view which can expand and collapse to read the text description. I bet you guys have seen this in a lot of android applications but might not know the name and its purpose. Well, below is a screenshot of the Instagram application on the Play store. This feature saves a lot of space, rather than laying out the huge chunks of information and occupying the entire page we can further use this option and can utilize the space"

    }
}