package com.example.mystage.fragments


//import com.example.mystage.BuildConfig
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mystage.R
import com.example.mystage.databinding.FragmentProfileBinding
import com.example.mystage.util.Resource
import com.example.mystage.util.showBottomNavigationView
import com.example.mystage.view.MainActivity
import com.example.mystage.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }

        binding.linearAllOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_allOrdersFragment)
        }
        binding.linearAdress.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addressFragment)
        }

        binding.linearBilling.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBillingFragment(
                0f,
                emptyArray()
            )
            findNavController().navigate(action)
        }

        binding.linearLogOut.setOnClickListener {
            showLogOutConfirmationDialog()
        }

//        binding.tvVersion.text = "Version ${BuildConfig.VERSION_CODE}"
        binding.tvVersion.text = "Version 1.0"

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressbarSettings.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressbarSettings.visibility = View.GONE
                        Glide.with(requireView()).load(it.data!!.imagePath).error(ColorDrawable(
                            Color.BLACK)).into(binding.imageUser)
                        binding.tvUserName.text = "${it.data.firstName} ${it.data.lastName}"
                        binding.tvUserEmail.text = "${it.data.email}"
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.progressbarSettings.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }
    private fun showLogOutConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Log Out")
            setMessage("Log out of account?")
            setNegativeButton("Cancel") { dialog, _ ->

                dialog.dismiss()
            }
            setPositiveButton("Yes") { dialog, _ ->
                viewModel.logout()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                dialog.dismiss()
            }
        }
        alertDialog.create()
        alertDialog.show()
    }
}