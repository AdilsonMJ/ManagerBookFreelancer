package com.example.managerbookfreelancer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.databinding.FragmentItemJobBinding
import com.example.managerbookfreelancer.resource.Resoucers

class AdapterListJobs(
    private val onClick: (JobEntity) -> Unit
): RecyclerView.Adapter<AdapterListJobs.viewHolder>() {


    private val asyncListDiff : AsyncListDiffer<JobEntity> = AsyncListDiffer(this, DiffCalback)


    inner class viewHolder(
        private val binding: FragmentItemJobBinding,
    ): RecyclerView.ViewHolder(binding.root)  {

        fun bind(jobModel: JobEntity, onClick: (JobEntity) -> Unit) {

            binding.tvData.text = Resoucers.fromLongToString(jobModel.weddingDay)
            binding.tvHour.text = jobModel.weddingTime
            binding.tvOwner.text = jobModel.ownerName
            binding.tvLocalization.text = jobModel.weddingCity
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


    fun upDateJobs(jobModel : List<JobEntity>){
        asyncListDiff.submitList(jobModel)
    }


    object DiffCalback : DiffUtil.ItemCallback<JobEntity>(){
        override fun areItemsTheSame(oldItem: JobEntity, newItem: JobEntity): Boolean {
            return oldItem.idJob == newItem.idJob
        }

        override fun areContentsTheSame(oldItem: JobEntity, newItem: JobEntity): Boolean {
            return oldItem == newItem
        }

    }


}