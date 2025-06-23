package com.example.merchandiser.presentation.mainMenu.recyclerViewAdapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.domain.TaskItem
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.graphics.toColorInt

class RecyclerViewAdapter: ListAdapter<TaskItem, RecyclerViewHolder>(RecyclerViewDiffItem()) {

    var onTaskItemClickListener: ((TaskItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.task_item,
            parent,
            false
        )
        return RecyclerViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        val task = getItem(position)
        holder.textViewName.text = task.name

        holder.itemView.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                holder.itemView.context,
                if (task.status) R.color.disactivated_color else R.color.spinner_color
            )
        )


        if (task.id == -1)
            holder.textViewDateTo.text = ""
        else
            holder.textViewDateTo.text = formatDate(task.date)

        holder.itemView.setOnClickListener {
            onTaskItemClickListener?.invoke(task)
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val formattedDate = outputFormat.format(date)
        return "до $formattedDate"
    }
}