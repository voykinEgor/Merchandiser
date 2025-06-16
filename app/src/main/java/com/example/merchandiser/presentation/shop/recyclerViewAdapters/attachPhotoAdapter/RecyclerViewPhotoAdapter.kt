package com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.domain.Photo
import java.net.URI

class RecyclerViewPhotoAdapter: ListAdapter<Photo, RecyclerViewPhotoHolder>(
    RecyclerViewPhotoDiffItem()
) {

    var onItemClickListener: ((Photo) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewPhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return RecyclerViewPhotoHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewPhotoHolder,
        position: Int
    ) {
        val photo = getItem(position)
        if (photo.id == 0){
            holder.photoItem.setImageResource(R.drawable.add_button)
            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(photo)
            }
        }else{
            if (photo.uri == URI("")){
                holder.photoItem.setImageResource(R.drawable.white_rect_with_radius)
            }else{
                holder.photoItem.setImageURI(photo.uri)
            }

        }

    }
}