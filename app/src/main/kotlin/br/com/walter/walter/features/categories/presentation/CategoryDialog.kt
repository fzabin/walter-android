package br.com.walter.walter.features.categories.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.walter.walter.R
import br.com.walter.walter.features.categories.domain.Category
import kotlinx.android.synthetic.main.addtransaction_category_dialog.view.*

class CategoryDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val createdView = createView()
    private var categoryRecycler = createdView.addtransaction_category_dialog_recycler

    private fun createView(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.addtransaction_category_dialog,
                viewGroup,
                false
            )
    }

    fun list(
        categories: List<Category>,
        selected: (category: Category) -> Unit
    ) {

        if (categories.isEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.addtransaction_category_dialog_message),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.addtransaction_category_dialog_title))
            .setView(createdView)
            .create()

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        categoryRecycler.layoutManager = layoutManager
        categoryRecycler.adapter = CategoryAdapter(
            context = context,
            categories = categories,
            dialog = dialog
        ) { category, position -> selected(categories[position]) }

        dialog.show()
    }
}