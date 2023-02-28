package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.databinding.FragmentItemProfessionalBinding


class AdapterListProfessional(
    private val onclick: (ClientEntity) -> Unit
) : RecyclerView.Adapter<AdapterListProfessional.viewHolder>()  {

    private val asyncListDiff : AsyncListDiffer<ClientEntity> = AsyncListDiffer(this, DiffCalback)

    inner class viewHolder(
        private val binding: FragmentItemProfessionalBinding
        ) : RecyclerView.ViewHolder(binding.root){

        fun bind(professionalEntity: ClientEntity, onclick: (ClientEntity) -> Unit){
            binding.tvItemProfessionalName.text = professionalEntity.name
            binding.tvItemProfessionalCellphone.text = professionalEntity.contact
            binding.tvItemProfessionalEmail.text = professionalEntity.email
            binding.root.setOnClickListener {
                onclick(professionalEntity)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemProfessionalBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int = asyncListDiff.currentList.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(asyncListDiff.currentList[position], onclick)
    }

    fun upDateProfessional(professionalEntity: List<ClientEntity>){
        asyncListDiff.submitList(professionalEntity)
    }

    object DiffCalback : DiffUtil.ItemCallback<ClientEntity>(){
        override fun areItemsTheSame(
            oldItem: ClientEntity,
            newItem: ClientEntity
        ): Boolean {
            return  oldItem.idClient == newItem.idClient
        }

        override fun areContentsTheSame(
            oldItem: ClientEntity,
            newItem: ClientEntity
        ): Boolean {
            return oldItem == newItem
        }


    }

}