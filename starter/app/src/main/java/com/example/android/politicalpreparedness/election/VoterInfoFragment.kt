package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoterInfoFragment : Fragment() {

    private val voterInfoViewModel: VoterInfoViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: Add ViewModel values and create ViewModel
        val election = VoterInfoFragmentArgs.fromBundle(requireArguments()).election
        voterInfoViewModel.setElection(election)
        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
        binding.viewModel = voterInfoViewModel

        //TODO: Handle loading of URLs
        binding.stateLocations.setOnClickListener {
            loadURL(voterInfoViewModel.stateLocations)
        }

        binding.stateBallot.setOnClickListener {
            loadURL(voterInfoViewModel.stateBallot)
        }
        //TODO: Handle save button UI state
        voterInfoViewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                binding.btnFollowOrSave.text = getString(R.string.txt_unfollow_elections)
            } else {
                binding.btnFollowOrSave.text = getString(R.string.txt_follow_election)
            }
        }
        //TODO: cont'd Handle save button clicks

        binding.btnFollowOrSave.setOnClickListener {
//            if (voterInfoViewModel.isSaved()) {
//                voterInfoViewModel.saveElection()
                voterInfoViewModel.saveOrDeleteElection()
                this.findNavController().popBackStack()
//            } else {
//                voterInfoViewModel.deleteElection()
//                this.findNavController().popBackStack()
//            }
        }


        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun loadURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}