package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.pvsb.core.model.PostalCodeResDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.popBackStack
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentCreateAddressBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

            btnSaveAddress.setOnClickListener {
                if (tiStreet.editText?.text.isNullOrEmpty() ||
                    tiNeighbour.editText?.text.isNullOrEmpty() ||
                    tiCity.editText?.text.isNullOrEmpty()
                ) {
                    Snackbar.make(
                        btnSaveAddress,
                        getString(R.string.error_mandatory_text_field_empty),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    saveAddress()
                }
            }
        }
        setUpObservers()
    }

    private fun saveAddress() {
        binding.apply {
            val zipCode = tiPostalCode.editText?.text?.toString()
            val street = tiStreet.editText?.text?.toString()
            val neighbour = tiNeighbour.editText?.text?.toString()
            val city = tiCity.editText?.text?.toString()
            val state = tiState.editText?.text?.toString()
            val complement = tiComplement.editText?.text?.toString()
            val number = tiNumber.editText?.text?.toString()
            val nick = tiAddressNick.editText?.text?.toString()

            val req = UserAddressDTO(
                zipCode.orEmpty(),
                street.orEmpty(),
                neighbour.orEmpty(),
                city.orEmpty(),
                state.orEmpty(),
                complement.orEmpty(),
                number.orEmpty(),
                nick.orEmpty()
            )

            lifecycleScope.launch {
                context?.getValueDS(stringPreferencesKey(USER_ID)) {
                    it?.let {
                        viewModel.saveAddress(it, req)
                    }
                }
            }
        }
    }

    private fun setUpObservers() {
        viewModel.postalCodeInfo
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<PostalCodeResDTO>(state = state,
                    onSuccess = {
                        binding.apply {
                            setTextOnEditText(tiStreet, it.street)
                            setTextOnEditText(tiNeighbour, it.neighbour)
                            setTextOnEditText(tiCity, it.city)
                            setTextOnEditText(tiState, it.state)
                        }
                    },
                    onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.saveAddress
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<Boolean>(state,
                    onSuccess = {
                        switchFragment(FragmentAddressesList(), clearBackStack = true)
                    }, onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setTextOnEditText(field: TextInputLayout, text: String) {
        field.editText?.setText(text)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}