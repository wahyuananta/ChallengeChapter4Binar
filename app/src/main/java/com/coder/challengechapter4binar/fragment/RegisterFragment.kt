package com.coder.challengechapter4binar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.DataUser
import com.coder.challengechapter4binar.R
import com.coder.challengechapter4binar.databinding.FragmentRegisterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())

        binding.btnDaftar.setOnClickListener {
            val username = binding.etUsername.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            val konfirmasiPassword = binding.etKonfirmasiPassword.text

            when {
                username.isNullOrEmpty() -> {
                    Toast.makeText(activity, "Username belum diisi", Toast.LENGTH_SHORT).show()
                }
                email.isNullOrEmpty() -> {
                    Toast.makeText(activity, "Email belum diisi", Toast.LENGTH_SHORT).show()
                }
                password.isNullOrEmpty() -> {
                    Toast.makeText(activity, "Password belum diisi", Toast.LENGTH_SHORT).show()
                }
                konfirmasiPassword.isNullOrEmpty() -> {
                    Toast.makeText(activity, "Konfirmasi password belum diisi", Toast.LENGTH_SHORT).show()
                }
                password.toString().lowercase() != konfirmasiPassword.toString().lowercase() -> {
                    Toast.makeText(activity, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val dataUser = DataUser(null, username.toString(), email.toString(), password.toString())

                    GlobalScope.async {
                        val result= mDb?.dataUserDao()?.addUser(dataUser)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()) {
                                Toast.makeText(activity, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}