package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.databinding.FragmentItemJobBinding

class AdapterListJobs(
    private val onClick: (JobModel) -> Unit
): RecyclerView.Adapter<AdapterListJobs.viewHolder>() {


    private val asyncListDiff : AsyncListDiffer<JobModel> = AsyncListDiffer(this, DiffCalback)


    inner class viewHolder(
        private val binding: FragmentItemJobBinding,
    ): RecyclerView.ViewHolder(binding.root)  {

        fun bind(jobModel: JobModel, onClick: (JobModel) -> Unit) {

            binding.tvData.text = jobModel.weedingDay
            binding.tvHour.text = jobModel.weedingTime
            binding.tvOwner.text = jobModel.ownerName
            binding.tvLocalization.text = jobModel.weedingCity


            binding.root.setOnClickListener{
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


    fun upDateJobs(jobModel : List<JobModel>){
        asyncListDiff.submitList(jobModel)
    }


    object DiffCalback : DiffUtil.ItemCallback<JobModel>(){
        override fun areItemsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobModel, newItem: JobModel): Boolean {
            return oldItem == newItem
        }

    }


}