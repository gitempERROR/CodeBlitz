package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.Days
import com.example.codeblitz.model.DaysInsert
import com.example.codeblitz.model.TaskSolutions
import com.example.codeblitz.model.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate

import java.util.Date
import javax.inject.Inject

//ViewModel главной страницы
@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    //Список заданий
    private val _tasks: MutableState<MutableList<Tasks>> = mutableStateOf(mutableListOf())
    val tasks: List<Tasks> get() = _tasks.value.toList()

    //Навигация в профиль
    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    init {
        viewModelScope.launch {
            //Получение завтрашней даты
            val tomorrow = LocalDate.now().plusDays(1)

            //Установка даты, с которой работаем, в зависимости от роли (администраторы добавляют задания на след. день
            val date = if (CurrentUser.isAdmin) tomorrow.toKotlinLocalDate() else Date()

            //Получение ID дня из базы
            var day: Days? = Constants.supabase.from("days").select() {
                filter {
                    Days::day_date eq date
                }
            }.decodeSingleOrNull()

            //Если день не записан в БД и пользователь администратор - создаем день
            if (day == null) {
                if (CurrentUser.isAdmin) {
                    try {
                        val nextDay = DaysInsert(
                            day_date = tomorrow.toKotlinLocalDate(),
                            0
                        )
                        Constants.supabase.from("days").insert(
                            nextDay
                        )
                        day = Constants.supabase.from("days").select() {
                            filter {
                                Days::day_date eq tomorrow.toKotlinLocalDate()
                            }
                        }.decodeSingleOrNull()
                    } catch (e: Exception) {
                        Log.e("supabase", "error inserting day $e")
                    }
                }
            }
            //Получаем список заданий
            if (day != null) {
                try {
                    _tasks.value = Constants.supabase.from("tasks").select() {
                        filter {
                            Tasks::day_id eq day.id
                        }
                    }.decodeList<Tasks>().toMutableList()
                } catch (e: Exception) {
                    Log.e("supabase", "error getting tasks $e")
                }
            }

            //Получаем информацию о статусах задачи для обычного пользователя
            if (!CurrentUser.isAdmin) {
                try {
                    _tasks.value.forEachIndexed { id, element ->
                        val columns = Columns.raw(
                            """
                            id,
                            user_id (
                              id,
                              firstname,
                              surname,
                              nickname,
                              role_id
                            ),
                            task_id,
                            language_id (
                              id,
                              language_name
                            ),
                            code,
                            current_status (
                              id,
                              status_name
                            ),
                            start_time,
                            end_time
                        """.trimIndent()
                        )
                        val solution = Constants.supabase.from("task_solutions").select(
                            columns = columns
                        ) {
                            filter {
                                TaskSolutions::user_id eq CurrentUser.userData!!.id
                                TaskSolutions::task_id eq element.id
                            }
                        }.decodeSingleOrNull<TaskSolutions>()

                        //Установка статуса задачи для отображения
                        if (solution == null) {
                            _tasks.value[id].task_status = "not started"
                        } else {
                            _tasks.value[id].task_status = solution.current_status.status_name
                        }
                    }
                } catch (e: Exception) {
                    Log.e("supabase", "error getting statuses $e")
                }
            }
            //Обновление списка задач для обновления на экране
            val buf = _tasks.value
            _tasks.value = mutableListOf()
            _tasks.value = buf
        }
    }
}