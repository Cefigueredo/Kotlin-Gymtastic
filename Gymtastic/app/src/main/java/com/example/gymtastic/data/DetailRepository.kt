package com.example.gymtastic.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtastic.R

/**
 * Author: Carlos Figueredo
 *
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class DetailRepository(private val detailSources: MutableList<DetailDataSource>
): RecyclerView.Adapter<DetailRepository.DetailViewHolder>() {
    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val curDetailSource = detailSources[position]
        holder.itemView.apply {

        }
    }

    override fun getItemCount(): Int {
        return detailSources.size
    }

}