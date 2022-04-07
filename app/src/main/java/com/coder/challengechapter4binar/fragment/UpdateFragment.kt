package com.coder.challengechapter4binar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.Club
import com.coder.challengechapter4binar.databinding.FragmentUpdateBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class UpdateFragment(var itemSelected : Club) : DialogFragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())

        binding.btnUpdate.setOnClickListener {
            val tanggal = binding.etTanggal.text
            val jam = binding.etJam.text

            when {
                tanggal.isNullOrEmpty() -> {
                    binding.ilTanggal.error = "Tanggal belum diisi"
                }
                jam.isNullOrEmpty() -> {
                    binding.ilJam.error = "Kick off belum diisi"
                }
                else -> {
                    val dataClub = itemSelected
                    dataClub.tanggal = tanggal.toString()
                    dataClub.jam = jam.toString()

                    GlobalScope.async {
                        val result = mDb?.clubDao()?.updateClub(dataClub)
                        activity?.runOnUiThread {
                            if (result != 0) {
                                Toast.makeText(it.context, "Jadwal pertandingan berhasil diupdate", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(it.context, "Gagal mengupdate jadwal pertandingan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    dialog?.dismiss()
                }
            }
        }
        binding.btnBatal.setOnClickListener {
            dialog?.dismiss()
        }
    }

}