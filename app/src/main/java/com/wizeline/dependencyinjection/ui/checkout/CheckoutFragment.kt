package com.wizeline.dependencyinjection.ui.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.databinding.FragmentCheckoutBinding
import com.wizeline.dependencyinjection.util.DateFormatter

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModels()
    lateinit var dateFormatter: DateFormatter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.tacoList.observe(viewLifecycleOwner,::startComposeView)
    }

    private fun startComposeView(tacoList: List<Taco>){
        Log.d("Test","tacoList: ${tacoList}")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = CheckoutFragment()

    }
}