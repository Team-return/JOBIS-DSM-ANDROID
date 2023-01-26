package com.jobis.jobis_android.feature.login

import androidx.activity.viewModels
import com.jobis.jobis_android.MainActivity
import com.jobis.jobis_android.R
import com.jobis.jobis_android.base.BaseActivity
import com.jobis.jobis_android.databinding.ActivityLoginBinding
import com.jobis.jobis_android.extension.repeatOnStarted
import com.jobis.jobis_android.util.startActivity
import com.jobis.jobis_android.viewmodel.LoginViewModel
import com.jobis.jobis_android.viewmodel.LoginViewModel.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    R.layout.activity_login,
) {

    private val vm by viewModels<LoginViewModel>()

    override fun initView() {
        initLoginButton()
    }

    private fun initLoginButton() {
        binding.login.setOnClickListener {
            val id = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (id.isNotEmpty() && password.isNotEmpty()) {
                postLogin(
                    id = id,
                    password = password,
                )
            }
        }
    }

    private fun postLogin(
        id: String,
        password: String,
    ) {
        vm.postLogin(
            id = id,
            password = password,
        )
    }

    override fun collectEvent() {
        repeatOnStarted {
            vm.loginEvent.collect { handleEvent(it) }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.MoveToMainActivity -> {
                startActivity(
                    context = this,
                    to = MainActivity::class.java,
                )
            }
            is Event.UnAuthorization -> {
                getString(R.string.login_unauthorization)
            }
            is Event.NotFound -> {
                getString(R.string.login_not_found)
            }
            // TODO show message by following design
        }
    }
}