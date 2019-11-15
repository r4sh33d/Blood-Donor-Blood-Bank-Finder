package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentBloodResultsBinding
import javax.inject.Inject

class BloodResultsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodResultsBinding
    private lateinit var viewModel: BloodResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodResultsViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_search_results), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}