package com.example.ezemkofi_mvvm.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ezemkofi_mvvm.R
import com.example.ezemkofi_mvvm.data.model.response.CategoryResponse
import com.example.ezemkofi_mvvm.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categoiyList: MutableList<CategoryResponse> = mutableListOf(),
    private val onClick: (CategoryResponse) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var selectedPosition = 0

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryResponse, position: Int) {
            binding.tvName.text = item.name

            if (position == selectedPosition) {
                binding.tvName.setBackgroundResource(R.drawable.bg_item_category_selected)
                binding.tvName.setTextColor(binding.root.context.getColor(R.color.bg))
            } else {
                binding.tvName.setBackgroundResource(R.drawable.bg_item_category_unselected)
                binding.tvName.setTextColor(binding.root.context.getColor(R.color.textPrimary))
            }

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoiyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoiyList[position], position)
    }

    fun setData(newCategories: List<CategoryResponse>) {
        categoiyList.clear()
        categoiyList.addAll(newCategories)
        notifyDataSetChanged()
    }
}