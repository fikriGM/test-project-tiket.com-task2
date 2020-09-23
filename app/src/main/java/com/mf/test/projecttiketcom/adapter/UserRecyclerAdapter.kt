package com.mf.test.projecttiketcom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mf.test.projecttiketcom.R
import com.mf.test.projecttiketcom.network.users.GetUsersResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_users.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserRecyclerAdapter : RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>, Filterable {

    var data: ArrayList<GetUsersResponse>
    var dataSearch: ArrayList<GetUsersResponse>
    var context: Context
    var itemClickListener: OnItemClickListener? = null

    constructor(context: Context) : super() {

        this.context = context
        this.data = ArrayList()
        this.dataSearch = ArrayList()

    }

    fun setDataValue(data: ArrayList<GetUsersResponse>?) {
        dataSearch.clear()
        this.data.clear()
        this.data.addAll(data!!)
        this.dataSearch.addAll(data)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charText = charSequence.toString().toLowerCase(Locale.getDefault())
                data.clear()
                if (charText.length == 0) {
                    data.addAll(dataSearch)
                } else {
                    for (us in dataSearch) {
                        if (charText.length != 0 && us.login!!.toLowerCase(Locale.getDefault())
                                .contains(charText)
                        ) {
                            data.add(us)
                        }
                    }
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                @Suppress("UNCHECKED_CAST")
                data = filterResults.values as ArrayList<GetUsersResponse>
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_users, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.userTv.text = data.get(position).login

        if (data.get(position).avatar_url!!.trim().length > 0) {
            Picasso.get().load(data.get(position).avatar_url)
                .into(holder.itemView.userImg)
        } else {
/*            holder.itemView.imageViewThumbnail.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.bg_default_image))*/
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View) : super(itemView) {
            itemView.setOnClickListener {

                itemClickListener?.onItemClicked(data.get(adapterPosition))
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClicked(users: GetUsersResponse)
    }
}