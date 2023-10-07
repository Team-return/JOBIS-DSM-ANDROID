package team.retum.jobis_android.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.ContainerHost
import team.retum.domain.exception.BadRequestException
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.ForbiddenException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.OfflineException
import team.retum.domain.exception.OnServerException
import team.retum.domain.exception.TimeoutException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

abstract class BaseViewModel<S : State, E : SideEffect> : ContainerHost<S, E>, ViewModel() {

    @Inject
    lateinit var application: Application
    protected fun getStringFromException(
        throwable: Throwable,
    ) = when (throwable) {
        is BadRequestException -> application.getString(R.string.bad_request_exception)
        is UnAuthorizationException -> application.getString(R.string.unauthorization_exception)
        is ForbiddenException -> application.getString(R.string.forbidden_exception)
        is NotFoundException -> application.getString(R.string.not_found_exception)
        is ConflictException -> application.getString(R.string.conflict_exception)
        is OnServerException -> application.getString(R.string.server_exception)
        is TimeoutException -> application.getString(R.string.timeout_exception)
        is OfflineException -> application.getString(R.string.offline_exception)
        else -> application.getString(R.string.other_exception)
    }
}
