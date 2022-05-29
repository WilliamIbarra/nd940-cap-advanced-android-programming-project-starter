package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ViewHolderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.utils.GenericAdapter

class ElectionListAdapter(private val clickListener: ElectionListener) :
    GenericAdapter<List<Election>>,
    ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        //return ElectionViewHolder.from(parent)
        return ElectionViewHolder(
            ViewHolderElectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(election)
        }
        holder.bind(clickListener, election)
    }


    //TODO: Create ElectionViewHolder
    class ElectionViewHolder(private val viewHolderElectionBinding: ViewHolderElectionBinding) :
        RecyclerView.ViewHolder(viewHolderElectionBinding.root) {
        fun bind(electionClickListener: ElectionListener, election: Election) {
            viewHolderElectionBinding.election = election
            viewHolderElectionBinding.clickListener = electionClickListener
            viewHolderElectionBinding.executePendingBindings()
        }

        //TODO: Add companion object to inflate ViewHolder (from)
        companion object {
            fun from(parent: ViewGroup): ElectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewHolderElectionBinding.inflate(layoutInflater, parent, false)

                return ElectionViewHolder(binding)
            }
        }
    }

    //TODO: Create ElectionDiffCallback
    class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem == newItem
        }

    }

    //TODO: Create ElectionListener
    class ElectionListener(val clickListener: (election: Election) -> Unit) {
        fun onClick(election: Election) = clickListener(election)
    }

    override fun setData(data: List<Election>) {
        submitList(data)
    }

}



