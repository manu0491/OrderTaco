package com.wizeline.dependencyinjection.ui.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.fragment.app.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.wizeline.dependencyinjection.databinding.FragmentCheckoutBinding
import com.wizeline.dependencyinjection.ui.checkout.compose.CheckoutScreen
import com.wizeline.dependencyinjection.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModels()
    @Inject lateinit var dateFormatter: DateFormatter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheckoutScreen(
                        onRemoveTaco = {taco ->
                            viewModel.removeTaco(taco)
                        },
                        dateFormatter = dateFormatter
                    )
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalAllTacos()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CheckoutFragment()

    }
}