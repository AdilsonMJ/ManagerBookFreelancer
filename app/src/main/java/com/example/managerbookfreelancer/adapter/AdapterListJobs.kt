package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.databinding.FragmentItemJobBinding
import com.example.managerbookfreelancer.model.JobModel
import com.example.managerbookfreelancer.viewModel.JobsViewModel

class AdapterListJobs(private val viewModel: JobsViewModel) : RecyclerView.Adapter<AdapterListJobs.viewHolder>() {


    private val asyncListDiff : AsyncListDiffer<JobModel> = AsyncListDiffer(this, DiffCalback)

    class viewHolder(
        private val binding: FragmentItemJobBinding,
        private val viewModel: JobsViewModel
    ):
        RecyclerView.ViewHolder(binding.root) {


        fun bind(job: JobModel) {
            binding.tvData.text = job.weedingDay.data
            binding.tvHour.text = job.weedingDay.hours
            binding.tvOwner.text = job.ownerJob.owner_name
            binding.tvLocalization.text = job.weedingDay.district
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemJobBinding.inflate(layoutInflater, parent, false)
        return viewHolder(binding, viewModel)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(asyncListDiff.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiff.currentList.size


    fun upDateJobs(job : List<JobModel>){
        asyncListDiff.submitList(job)
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