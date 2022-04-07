package com.coder.challengechapter4binar.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.ClubAdapter
import com.coder.challengechapter4binar.R
import com.coder.challengechapter4binar.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase? = null
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
        binding.tvUser.text = "Hai ${preferences.getString(LoginFragment.USERNAME,null)}"

        mDb = AppDatabase.getInstance(requireContext())
        binding.rvList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        fetchData()
        add()
        userLogout()
    }

    private fun add() {
        binding.fabNewItem.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_addFragment)
        }
    }

    private fun fetchData() {
        GlobalScope.launch {
            val clubList = mDb?.clubDao()?.getAllClub()
            activity?.runOnUiThread {
                clubList?.let {
                    val adapter = ClubAdapter(it)
                    binding.rvList.adapter = adapter
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun userLogout() {
        binding.btnLogout.setOnClickListener {
            val logoutDialog = AlertDialog.Builder(requireContext())
            logoutDialog.setTitle("Logout")
            logoutDialog.setMessage("Apakah anda yakin ingin logout?")
            logoutDialog.setPositiveButton("Logout") {p0, _ ->
                p0.dismiss()
                preferences.edit().clear().apply()
                findNavController().navigate(R.id.action_homeScreenFragment_to_loginFragment)
            }
            logoutDialog.setNegativeButton("Batal") {p0, _ ->
                p0.dismiss()
            }
            logoutDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

}