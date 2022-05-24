package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import co.fullstacklabs.androidkotlinchallenge.R
import co.fullstacklabs.androidkotlinchallenge.base.BaseFragment
import co.fullstacklabs.androidkotlinchallenge.base.viewBinding
import co.fullstacklabs.androidkotlinchallenge.databinding.FragmentNodesBinding
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeModel
import co.fullstacklabs.androidkotlinchallenge.extensions.observe
import org.koin.android.ext.android.inject

class NodesFragment: BaseFragment(R.layout.fragment_nodes) {
    private val binding by viewBinding(FragmentNodesBinding::bind)
    private val adapter: NodesAdapter by inject()
    private val viewModel: NodesViewModel by inject()

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        configureRecyclerView()
    }

    override fun setupObservers() {
        observe(viewModel.nodes) {
            adapter.data = it
        }
        observe(viewModel.state){
            binding.pbNodeFragment.isVisible = it == NodeContract.State.Loading
            when(it){
                is NodeContract.State.Success ->Toast.makeText(requireContext(), "information fetched successfully", Toast.LENGTH_LONG).show()
                is NodeContract.State.Error -> Toast.makeText(requireContext(), "We couldn't fetch all information", Toast.LENGTH_LONG).show()
                else -> {

                }
            }
        }
    }

    private fun configureRecyclerView() {
        binding.rvNodes.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = this@NodesFragment.adapter.apply {
                setHasStableIds(true)
                setOnItemClickListener(object: NodesAdapter.OnItemClickListener {
                    override fun onItemClick(model: NodeModel) {
                        viewModel.expandNode(node = model)
                    }
                })
            }
        }
    }
}