package team.retum.jobis_android.feature.root

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

abstract class BaseViewModel<S : State, E : SideEffect> : ContainerHost<S, E>, ViewModel() {
    protected fun getStringFromException(throwable: Throwable) = when (throwable) {
        is BadRequestException -> R.string.bad_request_exception
        is UnAuthorizationException -> R.string.unauthorization_exception
        is ForbiddenException -> R.string.forbidden_exception
        is NotFoundException -> R.string.not_found_exception
        is ConflictException -> R.string.conflict_exception
        is OnServerException -> R.string.server_exception
        is TimeoutException -> R.string.timeout_exception
        is OfflineException -> R.string.offline_exception
        else -> R.string.other_exception
    }
}
