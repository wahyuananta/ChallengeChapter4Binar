package com.coder.challengechapter4binar.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.Club
import com.coder.challengechapter4binar.R
import com.coder.challengechapter4binar.Repository
import com.coder.challengechapter4binar.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : DialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase? = null
    private var cal = Calendar.getInstance()

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
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AppDatabase.getInstance(requireContext())

        binding.btnTanggal.setOnClickListener {
            datePickerDialog()
        }

        binding.btnJam.setOnClickListener {
            timePickerDialog()
        }

        binding.btnTambah.setOnClickListener {
            val liga = binding.etLiga.text
            val home = binding.etHome.text
            val away = binding.etAway.text
            val tanggal = binding.tvTanggal.text
            val jam = binding.tvJam.text

            when {
                liga.isNullOrEmpty() -> {
                    binding.ilLiga.error = getString(R.string.liga_belum_diisi)
                }
                home.isNullOrEmpty() -> {
                    binding.ilHome.error = getString(R.string.tim_home_belum_diisi)
                }
                away.isNullOrEmpty() -> {
                    binding.ilAway.error = getString(R.string.tim_away_belum_diisi)
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
//                        val result = mDb?.clubDao()?.insertClub(dataClub)
                        val result = mDb?.repository()?.insert(dataClub)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()) {
                                Toast.makeText(requireContext(),"Pertandingan berhasil ditambahkan ke jadwal", Toast.LENGTH_SHORT).show()
                            } else {
                                val snackbar = Snackbar.make(it,"Gagal menambahkan pertandingan ke jadwal, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
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

    private fun datePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.tvTanggal.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(cal.time)
        }
        DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(
            Calendar.MONTH), cal.get(Calendar.MONTH)).show()
    }

    private fun timePickerDialog() {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            binding.tvJam.text = SimpleDateFormat("HH.mm", Locale.US).format(cal.time)
        }
        TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(
            Calendar.MINUTE), true).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}