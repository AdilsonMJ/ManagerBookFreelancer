package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.databinding.FragmentItemProfessionalBinding


class AdapterListProfessional(
    private val onclick: (ClientModelItem) -> Unit
) : RecyclerView.Adapter<AdapterListProfessional.viewHolder>()  {

    private val asyncListDiff : AsyncListDiffer<ClientModelItem> = AsyncListDiffer(this, DiffCalback)

    inner class viewHolder(
        private val binding: FragmentItemProfessionalBinding
        ) : RecyclerView.ViewHolder(binding.root){

        fun bind(clientModelItem: ClientModelItem, onclick: (ClientModelItem) -> Unit){
            binding.tvItemProfessionalName.text = clientModelItem.name
            binding.tvItemProfessionalCellphone.text = clientModelItem.contact
            binding.tvItemProfessionalEmail.text = clientModelItem.email
            binding.root.setOnClickListener {
                onclick(clientModelItem)
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

    fun upDateProfessional(clientModelItem: List<ClientModelItem>){
        asyncListDiff.submitList(clientModelItem)
    }

    object DiffCalback : DiffUtil.ItemCallback<ClientModelItem>(){
        override fun areItemsTheSame(
            oldItem: ClientModelItem,
            newItem: ClientModelItem
        ): Boolean {
            return  oldItem.idClient == newItem.idClient
        }

        override fun areContentsTheSame(
            oldItem: ClientModelItem,
            newItem: ClientModelItem
        ): Boolean {
            return oldItem == newItem
        }


    }

}