package com.coder.challengechapter4binar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coder.challengechapter4binar.databinding.ListItemBinding
import com.coder.challengechapter4binar.fragment.DeleteFragment
import com.coder.challengechapter4binar.fragment.UpdateFragment

class ClubAdapter(val listClub: List<Club>) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Club>() {
        override fun areItemsTheSame(oldItem: Club, newItem: Club): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Club, newItem: Club): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<Club>) = differ.submitList(value)

    class ViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =differ.currentList[position]

        holder.binding.apply {
            tvLiga.text = data.liga
            tvHome.text = data.tim_home
            tvAway.text = data.tim_away
            tvTanggal.text = data.tanggal
            tvJam.text = data.jam

            ivEdit.setOnClickListener {
                val activity = it.context as MainActivity
                val dialogFragment = UpdateFragment(data)
                dialogFragment.show(activity.supportFragmentManager, null)
            }

            ivDelete.setOnClickListener {
                val activity = it.context as MainActivity
                val dialogFragment = DeleteFragment(data)
                dialogFragment.show(activity.supportFragmentManager, null)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}