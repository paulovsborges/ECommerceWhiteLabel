package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.PostalCodeResDTO
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.popBackStack
import com.pvsb.ecommercewhitelabel.databinding.FragmentCreateAddressBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentCreateAddress : Fragment() {

    private var _binding: FragmentCreateAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetUp()
    }

    private fun initialSetUp() {

        binding.apply {
            tiPostalCode.editText?.doAfterTextChanged {
                if (it?.length == 8) {
                    viewModel.getPostalCodeInfo(it.toString())
                }
            }

            ivBack.setOnClickListener {
                popBackStack()
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.postalCodeInfo
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<PostalCodeResDTO>(state = state,
                    onSuccess = {
                        binding.apply {
                            tiStreet.editText?.setText(it.street)
                            tiNeighbour.editText?.setText(it.neighbour)
                            tiCity.editText?.setText(it.city)
                            tiState.editText?.setText(it.state)
                        }
                    },
                    onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}