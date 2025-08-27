package com.example.ezemkofi_mvvm.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.example.ezemkofi_mvvm.data.model.category.CategoryResponse
import com.example.ezemkofi_mvvm.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categoiyList: MutableList<CategoryResponse> = mutableListOf(),
    private val onClick: (CategoryResponse) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryResponse) {
            binding.tvName.text = item.name

            binding.root.setOnClickListener {
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
        holder.bind(categoiyList[position])
    }

    fun setData(newCategories: List<CategoryResponse>) {
        categoiyList.clear()
        categoiyList.addAll(newCategories)
        notifyDataSetChanged()
    }
}