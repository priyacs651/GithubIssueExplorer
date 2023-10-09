package com.example.githubissueexplorer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItem
import com.example.githubissueexplorer.data.repo.IssueRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(val repo: IssueRepo) : ViewModel() {

    private val _issueResponseLiveData = MutableLiveData<List<IssueResponseItem>>()
    val issueResponseLiveData: LiveData<List<IssueResponseItem>> = _issueResponseLiveData

    private val _showErrorLiveData = MutableLiveData<String>()
    val showErrorLiveData: LiveData<String> = _showErrorLiveData


    fun getAllDataFromRetrofit() {
        viewModelScope.launch {
            when(val response = repo.getIssuesFromRemote()) {
                is ApiResponse.Success -> {
                    _issueResponseLiveData.postValue(response.data)
                }
                is ApiResponse.Failure -> {
                    _showErrorLiveData.postValue(response.errorMessage)
                }
            }
        }
    }

    fun getAllDataFromDb() {
        viewModelScope.launch {
            _issueResponseLiveData.postValue(repo.getIssuesFromDb())
        }
    }

}