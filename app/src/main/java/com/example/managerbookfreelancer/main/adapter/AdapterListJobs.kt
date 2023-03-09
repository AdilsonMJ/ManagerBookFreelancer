package com.example.managerbookfreelancer.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.main.core.model.JobModelItem
import com.example.managerbookfreelancer.databinding.FragmentItemJobBinding

class AdapterListJobs(
    private val listener: OnButtonClickListener
): RecyclerView.Adapter<AdapterListJobs.viewHolder>() {

    private val asyncListDiff : AsyncListDiffer<JobModelItem> = AsyncListDiffer(this, DiffCalback)

    inner class viewHolder(
        private val binding: FragmentItemJobBinding,
    ): RecyclerView.ViewHolder(binding.root)  {

        fun bind(jobModel: JobModelItem, listener: OnButtonClickListener) {

            binding.tvItemJobDateEvent.text = jobModel.date
            binding.tvItemJobTimeEvent.text = jobModel.time
            binding.tvItemJobClient.text = jobModel.clientName
            binding.tvItemJobLocation.text = jobModel.city
            binding.tvItemJobCustomerEndUser.text = jobModel.customerEndUser

            binding.IMGJobButtonAction.setOnClickListener(){
                listener.onButtonClick(jobModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemJobBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(asyncListDiff.currentList[position], listener)
    }

    override fun getItemCount(): Int = asyncListDiff.currentList.size


    fun upDateJobs(jobModel : List<JobModelItem>){
        asyncListDiff.submitList(jobModel)
    }


    object DiffCalback : DiffUtil.ItemCallback<JobModelItem>(){
        override fun areItemsTheSame(oldItem: JobModelItem, newItem: JobModelItem): Boolean {
            return oldItem.idJob == newItem.idJob
        }

        override fun areContentsTheSame(oldItem: JobModelItem, newItem: JobModelItem): Boolean {
            return oldItem == newItem
        }

    }
}