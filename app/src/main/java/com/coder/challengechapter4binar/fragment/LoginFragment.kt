package com.coder.challengechapter4binar.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.R
import com.coder.challengechapter4binar.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase? = null

    companion object {
        const val LOGINUSER = "login_username"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())

        val preferences = this.activity?.getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)
        if (preferences!!.getString(USERNAME, null) != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text
            val password = binding.etPassword.text

            when {
                username.isNullOrEmpty() -> {
                    binding.ilUsername.error = "Username belum diisi"
                }
                password.isNullOrEmpty() -> {
                    binding.ilPassword.error = "Password belum diisi"
                }
                else -> {
                    GlobalScope.async {
                        val result = mDb?.dataUserDao()?.checkUser(username.toString(), password.toString())

                        activity?.runOnUiThread {
                            if (result == false) {
                                val snackbar = Snackbar.make(it,"Login gagal, coba periksa email atau password anda",
                                    Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
                            } else {
                                val editor : SharedPreferences.Editor = preferences.edit()
                                editor.putString(USERNAME, username.toString())
                                editor.putString(PASSWORD, password.toString())
                                editor.apply()
                                Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}