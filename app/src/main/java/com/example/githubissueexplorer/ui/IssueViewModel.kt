package com.example.githubissueexplorer.ui

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
import com.example.githubissueexplorer.data.repo.IssueRepo
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val repo: IssueRepo) : ViewModel() {

  private val _issueResponseLiveData = MutableLiveData<List<IssueResponseItemModel>>()

  val issueResponseLiveData: LiveData<List<IssueResponseItemModel>> = _issueResponseLiveData

  private val _showErrorLiveData = MutableLiveData<String>()
  val showErrorLiveData: LiveData<String> = _showErrorLiveData

  fun getAllDataFromRemote() {
    viewModelScope.launch {
      when (val response = repo.getIssuesFromRemote()) {
        is ApiResponse.Success -> {
          _issueResponseLiveData.postValue(response.data)
        }

        is ApiResponse.Failure -> {
          _showErrorLiveData.postValue(response.errorMessage)
        }
      }
    }
  }

  fun getAllDataFromLocal() {
    viewModelScope.launch() {
      _issueResponseLiveData.postValue(repo.getIssuesFromLocal())
    }
  }

  fun deleteIssueFromLocal(id: Int) {
    viewModelScope.launch() {
      repo.deleteIssueFromLocal(id)
      getAllDataFromLocal()
    }
  }

  fun updateIssueFromLocal(id: Int, login: String, title: String) {
    viewModelScope.launch() {
      repo.updateIssueFromLocal(id, login, title)
      getAllDataFromLocal()
    }
  }
}