package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel

class BlockAdapter(): ListAdapter<NodeBlockModel, BlockViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return BlockViewHolder.create(parent = parent)
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NodeBlockModel>(){
            override fun areItemsTheSame(
                oldItem: NodeBlockModel,
                newItem: NodeBlockModel
            ): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: NodeBlockModel,
                newItem: NodeBlockModel
            ): Boolean {
                return newItem == oldItem
            }

        }
    }
}