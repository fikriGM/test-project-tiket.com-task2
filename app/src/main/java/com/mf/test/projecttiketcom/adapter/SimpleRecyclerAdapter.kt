package com.mf.test.projecttiketcom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mf.test.projecttiketcom.R
import com.mf.test.projecttiketcom.network.users.GetUsersResponse
import com.mf.test.projecttiketcom.utils.log
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

/*
* Created by Fikri on 20/09/2020
* */
class SimpleRecyclerAdapter<O>(
    private val context: Context,
    private val tag: UsersAdapterTag,
    private var itemList: ArrayList<O>? = null,
    private var onDeleteClick: (O) -> Unit?,
    private val onItemClick: (O) -> Unit
) : RecyclerView.Adapter<SimpleRecyclerAdapter<O>.SimpleRecyclerViewHolder>(), Filterable {

    var countryFilterList = ArrayList<GetUsersResponse>()
    var dataSearch = ArrayList<GetUsersResponse>()

    companion object {
        enum class UsersAdapterTag {
            LIST_GET_USERS

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRecyclerViewHolder {
        when (tag) {
            UsersAdapterTag.LIST_GET_USERS -> {
                return SimpleRecyclerViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_users, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        if (!itemList.isNullOrEmpty()) {
            return itemList!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: SimpleRecyclerViewHolder, position: Int) {
        if (itemList != null) {
            holder.bind(itemList!![position])
        }
    }

    inner class SimpleRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //User
        private var userImage: ImageView? = null
        private var userTxt: TextView? = null

        init {
            when (tag) {
                UsersAdapterTag.LIST_GET_USERS -> {
                    userImage = view.findViewById(R.id.userImg)
                    userTxt = view.findViewById(R.id.userTv)
                }
            }
        }

        fun bind(item: O) {
            when (tag) {
                UsersAdapterTag.LIST_GET_USERS -> {
                    if (item is GetUsersResponse) {
                        userTxt?.text = item.login.toString()
                        val avatar = item.avatar_url
                        Picasso.get()
                            .load(avatar)
                            .placeholder(R.drawable.empty_product)
                            .error(R.drawable.new_add_photo_background)
                            .fit()
                            .into(userImage)
                    }
                }
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charText = charSequence.toString().toLowerCase(Locale.getDefault())

                countryFilterList?.clear()
                if (charText.length == 0) {
                    countryFilterList?.addAll(dataSearch)
                } else {
                    for (video in countryFilterList) {
                        if (charText.length != 0 && video.login?.toLowerCase(Locale.getDefault())!!.contains(charText)) {
                            countryFilterList?.add(video)
                        }
                    }
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                @Suppress("UNCHECKED_CAST")
                countryFilterList = filterResults.values as ArrayList<GetUsersResponse>
                notifyDataSetChanged()
            }
        }
    }
}