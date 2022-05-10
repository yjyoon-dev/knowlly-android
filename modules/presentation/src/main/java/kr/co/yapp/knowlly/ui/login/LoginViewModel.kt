package kr.co.yapp.knowlly.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.co.yapp.knowlly.ui.base.BaseViewModel
import kr.co.yapp.knowlly.ui.model.LoginTypeModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun login(type: LoginTypeModel) {
        _isLoggedIn.value = true
    }
}
