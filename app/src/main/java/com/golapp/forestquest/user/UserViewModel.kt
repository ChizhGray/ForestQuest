package com.golapp.forestquest.user

import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.User
import com.golapp.forestquest.room.interfaces.UserDao
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

class UserViewModel(
    private val userDao: UserDao
) : ContainerHost<UserState, UserSideEffect>, ViewModel() {
    override val container: Container<UserState, UserSideEffect> =
        container(
            initialState = UserState(
                users = emptyList()
            )
        )

    init {
        getUsers()
    }

    fun getUsers() = intent {
        val users = userDao.getAllUsers()
        reduce { state.copy(users = users) }
    }

    fun insertUser(user: User) = intent {
        userDao.insertUser(user)
        getUsers()
    }

    fun deleteUser(user: User) = intent {
        userDao.deleteUser(user)
        getUsers()
    }
}
