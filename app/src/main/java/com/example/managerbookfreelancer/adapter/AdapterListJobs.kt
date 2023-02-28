package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.databinding.FragmentItemJobBinding

class AdapterListJobs(
    private val onClick: (JobModelItem) -> Unit,
): RecyclerView.Adapter<AdapterListJobs.viewHolder>() {

    private val asyncListDiff : AsyncListDiffer<JobModelItem> = AsyncListDiffer(this, DiffCalback)

    inner class viewHolder(
        private val binding: FragmentItemJobBinding,
    ): RecyclerView.ViewHolder(binding.root)  {

        fun bind(jobModel: JobModelItem, onClick: (JobModelItem) -> Unit) {

            binding.tvItemJobDateEvent.text = jobModel.date
            binding.tvItemJobTimeEvent.text = jobModel.time
            binding.tvItemJobClient.text = jobModel.clientName
            binding.tvItemJobLocation.text = jobModel.city

            binding.root.setOnClickListener(){
                onClick(jobModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemJobBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(asyncListDiff.currentList[position], onClick)
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