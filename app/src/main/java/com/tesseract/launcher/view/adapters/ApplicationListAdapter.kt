package com.tesseract.launcher.view.adapters

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tesseract.launcher.R
import com.tesseract.launcher.databinding.ItemAppBackupBinding
import com.tesseract.launcherlibrary.AppBackListElement
import com.tesseract.launcher.utils.goneIf
import com.tesseract.launcher.utils.inflate


class ApplicationListAdapter(
    var appList: ArrayList<AppBackListElement>,
    private val onApplicationClick: (AppBackListElement) -> Unit,
    private val onDeleteClick: (AppBackListElement) -> Unit
) : RecyclerView.Adapter<ApplicationListAdapter.AppBackupViewHolder>(), Filterable {

    var appListFilter = ArrayList<AppBackListElement>()

    init {
        appListFilter = appList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppBackupViewHolder {
        val viewLayout: ItemAppBackupBinding = parent.inflate(R.layout.item_app_backup)
        return AppBackupViewHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: AppBackupViewHolder, position: Int) {
        val item = appList[position]
        holder.bindingView.item = item
        holder.itemView.setOnClickListener {
            onApplicationClick(item)
        }
        holder.bindingView.ivMore.goneIf(item.isSystemApp)
        holder.bindingView.ivMore.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    appList = appListFilter
                } else {
                    val backupList = ArrayList<AppBackListElement>()
                    for (element in appListFilter) {
                        if (element.title.toLowerCase().contains(charString.toLowerCase())) {
                            backupList.add(element)
                        }
                    }
                    appList = backupList
                }

                val filterResults = FilterResults()
                filterResults.values = appList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                appList = filterResults.values as ArrayList<AppBackListElement>
                notifyDataSetChanged()
            }
        }
    }

    class AppBackupViewHolder(val bindingView: ItemAppBackupBinding) :
        RecyclerView.ViewHolder(bindingView.root)
}
