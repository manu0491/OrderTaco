package com.wizeline.dependencyinjection.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.databinding.FragmentOrderBinding
import com.wizeline.dependencyinjection.navigation.AppNavigator
import com.wizeline.dependencyinjection.navigation.Screens


class OrderFragment : Fragment() {

    private val viewModel:OrderViewModel by viewModels()

    private var _binding: FragmentOrderBinding? = null

    lateinit var navigation: AppNavigator
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateSpinner()
        setupListeners()
        setupObservers()
    }


    private fun populateSpinner(){
        val spinner = binding.tacoSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tacos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }
    }

    private fun setupListeners(){
        with(binding) {
            binding.tacoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position != 0) {
                        val type: String = parent?.getItemAtPosition(position) as String
                        viewModel.setType(type)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            buttonAdd.setOnClickListener {
                viewModel.addTacoToOrder()
            }

            buttonCheck.setOnClickListener {
                navigation.navigateTo(Screens.CHECKOUT)
            }
            radioGroupTortillasSelector.setOnCheckedChangeListener { radioGroup, checkedId ->
                when(checkedId) {
                    R.id.radio_corn -> {
                        viewModel.setTortilla(radioCorn.text.toString())
                    }
                    R.id.radio_wheat -> {
                        viewModel.setTortilla(radioWheat.text.toString())
                    }
                }
            }
            note.doOnTextChanged { text, start, before, count ->
                viewModel.setNote(text.toString())
            }
            viewModel.setTortilla(radioCorn.text.toString())
        }
    }

    private fun setupObservers(){
        viewModel.taco.observe(viewLifecycleOwner,:: checkTacoParams)
    }

    private fun checkTacoParams(taco: Taco){
        with(binding) {
            buttonAdd.isVisible = taco.type.isNotEmpty() && taco.tortilla.isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = OrderFragment()
    }
}