package com.wizeline.dependencyinjection.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.widget.doOnTextChanged
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.databinding.FragmentOrderBinding


class OrderFragment : Fragment() {


    private var _binding: FragmentOrderBinding? = null
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
        setup()
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

    private fun setup(){
        with(binding) {

            binding.tacoSpinner.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

                }
            buttonAdd.setOnClickListener {

            }

            buttonCheck.setOnClickListener {

            }
            radioGroupTortillasSelector.setOnCheckedChangeListener { radioGroup, checkedId ->
                when(checkedId) {
                    R.id.radio_corn -> {

                    }
                    R.id.radio_wheat -> {

                    }
                }
            }
            note.doOnTextChanged { text, start, before, count ->

            }

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