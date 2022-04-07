package com.coder.challengechapter4binar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.Club
import com.coder.challengechapter4binar.databinding.FragmentAddBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddFragment : DialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    var mDb: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())

        binding.btnTambah.setOnClickListener {
            val liga = binding.etLiga.text
            val home = binding.etHome.text
            val away = binding.etAway.text
            val tanggal = binding.etTanggal.text
            val jam = binding.etJam.text

            when {
                liga.isNullOrEmpty() -> {
                    binding.ilLiga.error = "Liga belum diisi"
                }
                home.isNullOrEmpty() -> {
                    binding.ilHome.error = "Tim home belum diisi"
                }
                away.isNullOrEmpty() -> {
                    binding.ilAway.error = "Tim away belum diisi"
                }
                tanggal.isNullOrEmpty() -> {
                    binding.ilTanggal.error = "Tanggal belum diisi"
                }
                jam.isNullOrEmpty() -> {
                    binding.ilJam.error = "Kick off belum diisi"
                }
                else -> {
                    val dataClub = Club(null,
                        liga.toString(),
                        home.toString(),
                        away.toString(),
                        tanggal.toString(),
                        jam.toString()
                    )

                    GlobalScope.async {
                        val result = mDb?.clubDao()?.insertClub(dataClub)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()) {
                                Toast.makeText(requireContext(),"Pertandingan berhasil ditambahkan ke jadwal", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(),"Gagal menambahkan pertandingan ke jadwal", Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}