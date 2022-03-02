package com.abdelmageed.nagwaandroidtask.ui.adapter

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelmageed.nagwaandroidtask.R
import com.abdelmageed.nagwaandroidtask.data.responses.FilesResponsesItem
import com.abdelmageed.nagwaandroidtask.databinding.ItemFileBinding
import com.abdelmageed.nagwaandroidtask.utils.SelectedItems
import java.io.File


class FilesAdapter(
    val context: Context,
    val list: List<FilesResponsesItem>,
    val items: SelectedItems,
    val files: Array<File>
) : RecyclerView.Adapter<FilesAdapter.MyHolder>() {

    lateinit var binding: ItemFileBinding

    var selectedItems: MutableList<String> = mutableListOf<String>()

    class MyHolder(binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_file, parent, false
        )
        val myHolder = MyHolder(binding)
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val directory = list.get(position).url.substringBeforeLast("/")
        val fullName = list.get(position).url.substringAfterLast("/")
        val fileName = fullName.substringBeforeLast(".")
        val extension = fullName.substringAfterLast(".")
        binding.tvName.setText(fileName + "." + extension)

        for (file in files) {
            if (file.name.equals(fileName + "." + extension)) {
                binding.chFile.isChecked = true
                binding.chFile.isEnabled = false
                val content = SpannableString(fileName + "." + extension)
                content.setSpan(UnderlineSpan(), 0, content.length, 0)
                binding.tvName.setText(content)
            }
        }


        binding.tvName.setOnClickListener {
            if (!binding.chFile.isEnabled) {
                for (file in files) {
                    if (file.name.equals(fileName + "." + extension)) {
                        val fil = File(file.path)
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.getPackageName().toString() + ".provider",
                            fil
                        )
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(uri)
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        context.startActivity(intent)
                    }
                }
            }
        }
        binding.chFile.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                selectedItems.add(list.get(position).url)
                items.getSelectedItems(selectedItems)
            } else {
                if (selectedItems.contains(list.get(position).url)) {
                    selectedItems.remove(list.get(position).url)
                    items.getSelectedItems(selectedItems)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}