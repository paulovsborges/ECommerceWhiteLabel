package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding

class ActivityProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
//            tvProductPrice.text = test.price
//            tvProductTitle.text = test.title
        }
    }
}