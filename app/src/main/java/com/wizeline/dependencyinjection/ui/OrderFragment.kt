package com.wizeline.dependencyinjection.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        //https://developer.android.com/develop/ui/views/components/spinner
        populateSpinner()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = OrderFragment()
    }
}