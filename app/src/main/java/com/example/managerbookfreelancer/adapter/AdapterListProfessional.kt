package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.core.entity.ProfessionalEntity
import com.example.managerbookfreelancer.databinding.FragmentItemProfessionalBinding


class AdapterListProfessional : RecyclerView.Adapter<AdapterListProfessional.viewHolder>()  {

    private val asyncListDiff : AsyncListDiffer<ProfessionalEntity> = AsyncListDiffer(this, DiffCalback)

    inner class viewHolder(private val binding: FragmentItemProfessionalBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(professionalEntity: ProfessionalEntity){
            binding.TVNameProfessional.text = professionalEntity.name
            binding.TVCellphone.text = professionalEntity.contact
            binding.TVEmailProfessional.text = professionalEntity.email
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemProfessionalBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int = asyncListDiff.currentList.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(asyncListDiff.currentList[position])
    }

    fun upDateProfessional(professionalEntity: List<ProfessionalEntity>){
        asyncListDiff.submitList(professionalEntity)
    }

    object DiffCalback : DiffUtil.ItemCallback<ProfessionalEntity>(){
        override fun areItemsTheSame(
            oldItem: ProfessionalEntity,
            newItem: ProfessionalEntity
        ): Boolean {
            return  oldItem.idProfessional == newItem.idProfessional
        }

        override fun areContentsTheSame(
            oldItem: ProfessionalEntity,
            newItem: ProfessionalEntity
        ): Boolean {
            return oldItem == newItem
        }


    }

}