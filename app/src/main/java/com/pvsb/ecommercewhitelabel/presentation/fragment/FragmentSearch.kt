package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.pvsb.core.utils.ResultCode
import com.pvsb.core.utils.openActivity
import com.pvsb.ecommercewhitelabel.databinding.FragmentSearchBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductFilters

class FragmentSearch : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFilters.setOnClickListener {
            setActivityResult()
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == ResultCode.OK) {

                    val data =
                        it.data?.extras?.get(ActivityProductFilters().javaClass.simpleName) as Int

                    Toast.makeText(requireContext(), "$data", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setActivityResult() {
        resultLauncher?.launch(
            Intent(requireContext(), ActivityProductFilters::class.java)
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}