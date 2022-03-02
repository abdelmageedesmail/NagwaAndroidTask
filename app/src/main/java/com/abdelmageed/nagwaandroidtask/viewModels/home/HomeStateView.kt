package com.abdelmageed.nagwaandroidtask.viewModels.home

import com.abdelmageed.nagwaandroidtask.data.responses.FilesResponsesItem
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.File

sealed class HomeStateView {
    object empty : HomeStateView()
    class Response(var responses: MutableList<FilesResponsesItem>) : HomeStateView()
    class Download(var download: Boolean) : HomeStateView()
    class GetAllFiles(var files: Array<File>) : HomeStateView()
}
