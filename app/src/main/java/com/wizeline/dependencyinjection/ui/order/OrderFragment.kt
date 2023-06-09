package com.wizeline.dependencyinjection.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.databinding.FragmentOrderBinding
import com.wizeline.dependencyinjection.navigation.AppNavigator
import com.wizeline.dependencyinjection.navigation.Screens
import com.wizeline.dependencyinjection.ui.OrderViewModel
import com.wizeline.dependencyinjection.ui.TACO_STATE
import com.wizeline.dependencyinjection.ui.TacoUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class OrderFragment(
    private val viewModelFactory: ViewModelProvider.Factory? = null
) : Fragment() {

    //private val viewModel: OrderViewModel by viewModels()
    @VisibleForTesting
    val viewModel: OrderViewModel by viewModels {
        viewModelFactory ?: defaultViewModelProviderFactory
    }

    private var _binding: FragmentOrderBinding? = null

    @Inject lateinit var navigation: AppNavigator
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
        uiStateFlow()
        viewModel.setTortilla(binding.radioCorn.text.toString())
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
        }
    }

    private fun setupObservers(){
        viewModel.taco.observe(viewLifecycleOwner,:: checkTacoParams)
        //viewModel.tacoState.observe(viewLifecycleOwner,::checkTacoState)
    }

    private fun uiStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tacoState.collect {
                   when(it){
                       is TacoUiState.OrderingState -> checkTacoState(it.ordering)
                       else ->{}
                   }
                }
            }
        }
    }
    private fun checkTacoParams(taco: Taco){
        with(binding) {
            val tacoIsDone = taco.type.isNotEmpty() && taco.tortilla.isNotEmpty()
            buttonAdd.isVisible = tacoIsDone
        }
    }

    private fun checkTacoState(tacoState: TACO_STATE) {
        when(tacoState) {
            TACO_STATE.LOADING ->{}
            TACO_STATE.ORDERING ->{}
            TACO_STATE.DONE -> {
                populateSpinner()
                viewModel.setTortilla(binding.radioCorn.text.toString())
                binding.note.text.clear()
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