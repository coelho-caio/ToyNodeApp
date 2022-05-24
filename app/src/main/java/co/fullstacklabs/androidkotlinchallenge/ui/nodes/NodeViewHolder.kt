package co.fullstacklabs.androidkotlinchallenge.ui.nodes

import android.view.View
import androidx.core.content.ContextCompat
import co.fullstacklabs.androidkotlinchallenge.R
import co.fullstacklabs.androidkotlinchallenge.base.BaseViewHolder
import co.fullstacklabs.androidkotlinchallenge.databinding.ItemNodeBinding
import co.fullstacklabs.androidkotlinchallenge.domain.model.NodeModel

internal class NodeViewHolder(private val viewBinding: ItemNodeBinding) :
    BaseViewHolder(viewBinding) {

    private val blockAdapter: BlockAdapter = BlockAdapter()

    fun binding(node: NodeModel) {
        val context = viewBinding.root.context

        viewBinding.mtvTitle.text = node.name
        viewBinding.mtvUrl.text = node.url
        viewBinding.rvItemListNode.adapter = blockAdapter
        node.blocks?.let {
            blockAdapter.submitList(node.blocks)
        }

        if (node.online) {
            viewBinding.mtvStatus.text = context.getString(R.string.status_online).uppercase()
            viewBinding.mtvStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.blue_gray_700
                )
            )

            viewBinding.mcvIconStatus.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.success)
            )
        } else {
            viewBinding.mtvStatus.text = context.getString(R.string.status_offline).uppercase()
            viewBinding.mtvStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.blue_gray_200
                )
            )
            viewBinding.mcvIconStatus.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.danger)
            )
        }

        if (node.expanded) {
            viewBinding.rvItemListNode.visibility = View.VISIBLE
            viewBinding.ivArrowExpand.setImageResource(R.drawable.ic_arrow_up)

        } else {
            viewBinding.rvItemListNode.visibility = View.GONE
            viewBinding.ivArrowExpand.setImageResource(R.drawable.ic_arrow_down)
        }
    }
}