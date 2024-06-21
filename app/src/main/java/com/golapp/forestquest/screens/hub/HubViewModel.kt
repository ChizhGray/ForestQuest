package com.golapp.forestquest.screens.hub

import androidx.lifecycle.ViewModel
import com.golapp.forestquest.room.entities.User
import com.golapp.forestquest.room.interfaces.UserDao
import org.orbitmvi.orbit.*
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container

class HubViewModel(
    private val userDao: UserDao
) : ContainerHost<HubState, HubSideEffect>, ViewModel() {
    override val container: Container<HubState, HubSideEffect> =
        container(
            initialState = HubState(
                users = emptyList()
            )
        )

    init {
        getUsers()
    }

    private fun getUsers() = intent {
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
