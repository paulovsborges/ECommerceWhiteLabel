package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pvsb.core.firestore.model.ProductDTO
import com.pvsb.ecommercewhitelabel.databinding.ActivityProductDetailsBinding

class ActivityProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent?.getParcelableExtra<ProductDTO>("PRODUCT_NAME")

        binding.apply {
            tvProductPrice.text = data?.price.toString()
            tvProductTitle.text = data?.title

            Glide.with(this@ActivityProductDetails)
                .load(data?.imageUrl)
                .centerCrop()
                .into(ivProductImage)
        }
    }
}