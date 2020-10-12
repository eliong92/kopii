package com.eliong92.kopii.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eliong92.kopii.R
import com.eliong92.kopii.usecase.VenueViewObject
import kotlinx.android.synthetic.main.item_venue.view.*

class VenueAdapter(private val items: List<VenueViewObject>) : RecyclerView.Adapter<VenueAdapter.VenueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bindVenue(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.name_txt
        private val address = itemView.address_txt
        private val rating = itemView.rating_txt

        fun bindVenue(venue: VenueViewObject) {
            name.text = venue.name
            address.text = venue.address
            rating.text = venue.rating
        }
    }
}