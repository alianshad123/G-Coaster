package com.anshad.g_coaster.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anshad.basestructure.constants.Actions
import com.anshad.basestructure.model.Action
import com.anshad.basestructure.ui.BaseFragment
import com.anshad.g_coaster.R
import com.anshad.g_coaster.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override val viewModel: SplashViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        observeSplashLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.performAction(Action(Intent(Actions.ENTER_FULLSCREEN)))

    }

    override fun onPause() {
        super.onPause()
        viewModel.performAction(Action(Intent(Actions.EXIT_FULLSCREEN)))
    }

    private fun observeSplashLiveData() {
        viewModel.initSplashScreen()
        val observer = Observer<SplashViewModel.SplashModel> {
            viewModel.navigateToDashBoard()
        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }


}