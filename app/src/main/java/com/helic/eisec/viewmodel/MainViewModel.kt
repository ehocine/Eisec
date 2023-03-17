package com.helic.eisec.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.helic.eisec.model.task.Task
import com.helic.eisec.repository.Repository
import com.helic.eisec.utils.viewstate.SingleViewState
import com.helic.eisec.utils.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // Backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _singleViewState = MutableStateFlow<SingleViewState>(SingleViewState.Loading)

    private val _loginState = MutableStateFlow<ViewState>(ViewState.Idle)

    // The UI collects from this StateFlow to get its state update
    val feed = _viewState.asStateFlow()
    val singleTask = _singleViewState.asStateFlow()
    val loginState = _loginState.asStateFlow()


    // get all task
    fun getAllTask() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllTask().distinctUntilChanged().collect { result ->
            try {
                if (result.isEmpty()) {
                    _viewState.value = ViewState.Empty
                } else {
                    _viewState.value = ViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }

//    // get all list of emoji from Json
//    @OptIn(ExperimentalSerializationApi::class)
//    fun getAllEmoji(context: Context, searchQuery: String) = viewModelScope.launch {
//        try {
//            // read JSON file
//            val myJson = context.assetsopen("emoji.json").bufferedReader().use {
//                it.readText()
//            }
//
//            // format JSON
//            val format = Json {
//                ignoreUnknownKeys = true
//                prettyPrint = true
//                isLenient = true
//            }
//
//            // decode emoji list from json
//            val decodedEmoji = format.decodeFromString<List<EmojiItem>>(myJson).distinct()
//
//            // filter the emoji based on Aliases
//            val filteredEmojiAliases = decodedEmoji.filter { emojiAliases ->
//                emojiAliases.aliases.any {
//                    it.contains(searchQuery, ignoreCase = true)
//                } || emojiAliases.category.contains(searchQuery, ignoreCase = true)
//            }.distinct()
//
//            if (searchQuery.isEmpty()) {
//                _emojiViewState.value = EmojiViewState.Success(decodedEmoji)
//            } else {
//                if (filteredEmojiAliases.isEmpty()) {
//                    _emojiViewState.value = EmojiViewState.Empty
//                } else {
//                    _emojiViewState.value = EmojiViewState.Success(filteredEmojiAliases)
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            _emojiViewState.value = EmojiViewState.Error(exception = e)
//        }
//    }

    // insert source
    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    // delete source
    fun deleteTaskByID(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    // update status
    fun updateStatus(id: Int, isCompleted: Boolean) = viewModelScope.launch {
        repository.updateStatus(id, isCompleted)
    }

    // find task by id
    fun findTaskByID(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.find(id).distinctUntilChanged().collect { result ->
            try {
                if (result.title.isEmpty()) {
                    _singleViewState.value = SingleViewState.Empty
                } else {
                    _singleViewState.value = SingleViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }

    // get all task
    fun getTaskByPriority(priority: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getTaskByPriority(priority).distinctUntilChanged().collect { result ->
            try {
                if (result.isEmpty()) {
                    _viewState.value = ViewState.Empty
                } else {
                    _viewState.value = ViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }
}