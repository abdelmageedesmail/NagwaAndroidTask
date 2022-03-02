package com.abdelmageed.nagwaandroidtask.ui.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.nagwaandroidtask.R
import com.abdelmageed.nagwaandroidtask.data.responses.FilesResponsesItem
import com.abdelmageed.nagwaandroidtask.databinding.ActivityHomeBinding
import com.abdelmageed.nagwaandroidtask.databinding.PopupLoadingBinding
import com.abdelmageed.nagwaandroidtask.ui.MainApp
import com.abdelmageed.nagwaandroidtask.ui.adapter.FilesAdapter
import com.abdelmageed.nagwaandroidtask.utils.ProgressLoading
import com.abdelmageed.nagwaandroidtask.utils.SelectedItems
import com.abdelmageed.nagwaandroidtask.utils.Utils
import com.abdelmageed.nagwaandroidtask.viewModels.home.HomeStateView
import com.abdelmageed.nagwaandroidtask.viewModels.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import java.io.File
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), SelectedItems {
    private lateinit var filesAdapter: FilesAdapter
    private var responsesList: MutableList<FilesResponsesItem> = mutableListOf()
    lateinit var popupLoadingBinding: PopupLoadingBinding
    private var downloadedList: MutableList<String> = mutableListOf()
    lateinit var progressLoading: ProgressLoading
    var count = 0
    lateinit var progreDialog: ProgressDialog
    var files: Array<File> = emptyArray()

    @Inject
    lateinit var homeViewModel: HomeViewModel
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()
        setUpObserver()
    }

    private fun setUpObserver() {
        homeViewModel.getAllDownloadedFiles()
        lifecycleScope.launchWhenCreated {
            homeViewModel.stateView.collect {

                /**
                 * (State flow (GetAllFiles))first get all downloaded files if it exist
                 * (State flow (Response))then show Json response
                 * (From Adapter)after that i will capable of open downloaded file with it file format
                 * (State flow (download))or download any file
                 */

                when (it) {
                    is HomeStateView.GetAllFiles -> {
                        files = it.files
                        homeViewModel.getListData()
                    }

                    is HomeStateView.Response -> {
                        responsesList = it.responses
                        filesAdapter =
                            FilesAdapter(this@HomeActivity, responsesList, this@HomeActivity, files)
                        binding.rvFiles.adapter =
                            filesAdapter
                        binding.rvFiles.layoutManager = LinearLayoutManager(this@HomeActivity)
                    }
                    is HomeStateView.Download -> {
                        if (it.download) {
                            count++
                            progressLoading.insertIndex(count, downloadedList.size)
                            if (count.equals(downloadedList.size)) {
                                progressLoading.cancelLoading()
                                if (responsesList.size > 0) {
                                    responsesList.clear()
                                }
                                if (filesAdapter != null) {
                                    filesAdapter.notifyDataSetChanged()
                                }
                                homeViewModel.getAllDownloadedFiles()
                            }

                        } else {
                            progressLoading.cancelLoading()
                            Toast.makeText(
                                this@HomeActivity,
                                "Error Ocured please try again ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        ((application as MainApp)).appComponent.inject(this)
        Utils.addPermissions(this)
        binding.lifecycleOwner = this
        progreDialog = ProgressDialog(this)
        progressLoading = ProgressLoading(this)
        binding.btnDownload.setOnClickListener {
            if (downloadedList.size > 0) {
                Utils.addPermissions(this)
                progressLoading.showLoading()
                progressLoading.insertIndex(0, downloadedList.size)
                for (item in downloadedList) {
                    homeViewModel.download(item)
                }
            }
        }
    }


    override fun getSelectedItems(list: MutableList<String>) {
        downloadedList = list
    }


}