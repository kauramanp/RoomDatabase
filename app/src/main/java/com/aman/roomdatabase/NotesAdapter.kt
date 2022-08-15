package com.aman.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.roomdatabase.databinding.LayoutItemBinding

class NotesAdapter(var arrayList: ArrayList<Notes>, var click: NotesClick) :RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(var binding: LayoutItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.tvDate.setText(arrayList[position].date)
            binding.tvTitle.setText(arrayList[position].title)
            binding.tvDescription.setText(arrayList[position].description)
        }
        holder.binding.root.setOnClickListener{
            click.NotesClicked(arrayList[position])
        }
    }

    override fun getItemCount() = arrayList.size
}