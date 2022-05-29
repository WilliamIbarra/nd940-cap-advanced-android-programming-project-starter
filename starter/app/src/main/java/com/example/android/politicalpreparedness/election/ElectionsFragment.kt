package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import org.koin.android.ext.android.inject

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel
    private val electionsViewModel: ElectionsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: Add ViewModel values and create ViewModel
        //TODO: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = electionsViewModel

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters
        binding.upcomingElectionsRecyclerView.adapter = ElectionListAdapter(getElectionCallback())
        binding.savedElectionsRecyclerview.adapter = ElectionListAdapter(getElectionCallback())
        //TODO: Populate recycler adapters


        return binding.root
    }

    //TODO: Refresh adapters when fragment loads
    private fun getElectionCallback() = ElectionListAdapter.ElectionListener { election ->
        // TODO navigate to election detail
        if(election.newDivision.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.txt_argument_invalid), Toast.LENGTH_LONG).show()
        } else {
            navToElectionDetail(election)
        }
    }

    private fun navToElectionDetail(election: Election) {

            this.findNavController().navigate(
                actionElectionsFragmentToVoterInfoFragment(
                    election.id,
                    election.newDivision
                )
            )

    }

}