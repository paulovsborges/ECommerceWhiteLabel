package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.core.model.enums.PaymentType
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_HOME
import com.pvsb.core.utils.Constants.PrefsKeys.CART_ID
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.ecommercewhitelabel.databinding.FragmentPaymentConfirmationBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.MainActivity
import com.pvsb.ecommercewhitelabel.presentation.adapter.PaymentConfirmationProductsAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class FragmentPaymentConfirmation : Fragment() {

    private var _binding: FragmentPaymentConfirmationBinding? = null
    private val binding get() = _binding!!
    private val hostViewModel: PaymentViewModel by activityViewModels()
    private val productsAdapter = PaymentConfirmationProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        setUpData()
        binding.apply {
            rvProducts.adapter = productsAdapter

            ivBack.setOnClickListener {
                onBackPress()
            }
        }
    }

    private fun setUpData() {
        productsAdapter.submitList(hostViewModel.cartObj?.products)

        binding.apply {
            tvCity.text = hostViewModel.selectedAddress?.city?.formatLength()
            tvZipCode.text = hostViewModel.selectedAddress?.zipCode?.formatLength()
            tvStreet.text = hostViewModel.selectedAddress?.street?.formatLength()
            tvNeighbour.text = hostViewModel.selectedAddress?.neighbour?.formatLength()
            tvNumber.text = hostViewModel.selectedAddress?.number?.formatLength()
            tvPaymentValue.text = hostViewModel.cartObj?.total?.formatCurrency()
            tvPaymentMethod.text = if (hostViewModel.selectedPaymentMethod.ordinal == 0) {
                PaymentType.BILLET.label
            } else {
                PaymentType.PIX.label
            }

            btnFinishOrder.setOnClickListener {
                lifecycleScope.launch {
                    context?.getValueDS(stringPreferencesKey(USER_ID)) { userId ->
                        userId?.let {
                            context?.getValueDS(stringPreferencesKey(CART_ID)) { cartId ->
                                cartId?.let {
                                    val situations = listOf(
                                        OrderSituationEnum.PAYMENT.label,
                                        OrderSituationEnum.CONCLUDED.label,
                                        OrderSituationEnum.DELIVERY_ROUTE.label,
                                        OrderSituationEnum.IN_TRANSPORT.label,
                                    ).map {
                                        getString(it)
                                    }

                                    val randomIndex = Random.nextInt(0, 3)
                                    registerOrder(
                                        cartId,
                                        userId,
                                        situations[randomIndex]
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun registerOrder(cartId: String, userId: String, situation: String) {
        hostViewModel.registerOder(cartId, userId, situation)
            ?.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            ?.onEach { state ->
                handleResponse<Boolean>(
                    state,
                    onSuccess = {
                        context?.removeValueDS(stringPreferencesKey(CART_ID))

                        activity?.openActivity(MainActivity::class.java) {
                            it.action = BOTTOM_NAV_HOME
                            it.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    },
                    onError = {
                    }
                )
            }
            ?.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
