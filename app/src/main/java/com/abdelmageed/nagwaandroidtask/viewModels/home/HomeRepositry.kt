package com.abdelmageed.nagwaandroidtask.viewModels.home

import android.content.Context
import com.abdelmageed.nagwaandroidtask.data.remote.ApiInterface
import com.abdelmageed.nagwaandroidtask.data.responses.FilesResponsesItem
import com.abdelmageed.nagwaandroidtask.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class HomeRepositry @Inject constructor(val context: Context, val apiInterface: ApiInterface) {

    fun getListFiles() = flow<MutableList<FilesResponsesItem>> {
        Utils.convertJsonStringToGson(context)?.let { emit(it.toMutableList()) }
    }.flowOn(Dispatchers.IO)

    fun getAllFiles() = flow<Array<File>> {
        emit(Utils.getAllFiles())
    }.flowOn(Dispatchers.IO)


    fun download(
        url: String,
        fileName: String,
        fileExtension: String
    ) =
        flow<Boolean> {
            emit(
                Utils.saveFileUsingMediaStore(url, fileName, fileExtension)
            )
        }.flowOn(Dispatchers.IO)

}