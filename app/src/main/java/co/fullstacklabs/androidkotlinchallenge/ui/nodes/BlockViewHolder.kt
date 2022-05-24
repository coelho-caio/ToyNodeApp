package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.fullstacklabs.androidkotlinchallenge.databinding.ItemBlockBinding
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeBlockModel

class BlockViewHolder(private val itemBinding: ItemBlockBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {


    fun bind(blockModel: NodeBlockModel) {
        itemBinding.tvTitleItemBlock.text = blockModel.id
        itemBinding.tvSubtitleItemBlock.text = blockModel.attributes.data
    }

    companion object {
        fun create(parent: ViewGroup): BlockViewHolder {
            val itemBinding =
                ItemBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BlockViewHolder(itemBinding)
        }
    }
}