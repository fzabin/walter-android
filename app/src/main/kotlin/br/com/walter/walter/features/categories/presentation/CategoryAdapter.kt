package br.com.walter.walter.features.categories.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import br.com.walter.walter.R
import br.com.walter.walter.features.categories.domain.Category
import kotlinx.android.synthetic.main.addtransaction_category_item.view.*


class CategoryAdapter(
    private val categories: List<Category>,
    private val context: Context,
    private val dialog: AlertDialog,
    private var onItemClicked: (category: Category, position: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.let {
            it.bindView(category)
            it.itemView.setOnClickListener {
                onItemClicked(category, position)
                dialog.dismiss()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.addtransaction_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(category: Category) {
            val textName = itemView.addtransaction_category_item_description

            textName.text = category.description
        }
    }

}