package com.coder.challengechapter4binar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.coder.challengechapter4binar.AppDatabase
import com.coder.challengechapter4binar.Club
import com.coder.challengechapter4binar.R
import com.coder.challengechapter4binar.databinding.FragmentDeleteBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DeleteFragment(var itemSelected : Club) : DialogFragment() {
    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!
    private var mDb: AppDatabase?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
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

        binding.btnYa.setOnClickListener {
            val dataClub = itemSelected
            dataClub.liga
            dataClub.tim_home
            dataClub.tim_away
            dataClub.tanggal
            dataClub.jam

            GlobalScope.async {
                val result = mDb?.clubDao()?.deleteClub(dataClub)
                activity?.runOnUiThread {
                    if (result != 0) {
                        Toast.makeText(it.context, "Jadwal pertandingan berhasil dihapus", Toast.LENGTH_SHORT).show()
                    } else {
                        val snackbar = Snackbar.make(it,"Gagal menghapus jadwal pertandingan, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                        snackbar.setAction("Oke") {
                            snackbar.dismiss()
                        }
                        snackbar.show()
                    }
                }
            }
            dialog?.dismiss()
        }
        binding.btnTidak.setOnClickListener {
            dialog?.dismiss()
        }
    }

}