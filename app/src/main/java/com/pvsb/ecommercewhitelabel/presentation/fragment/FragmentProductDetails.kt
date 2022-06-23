package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding

class FragmentProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding


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