package team.retum.jobis_android.viewmodel.bugreport

import android.net.Uri
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.jobis_android.contract.bugreport.BugReportSideEffect
import team.retum.jobis_android.contract.bugreport.BugReportState
import team.retum.jobis_android.contract.bugreport.Position
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

internal class BugReportViewModel @Inject constructor(

) : BaseViewModel<BugReportState, BugReportSideEffect>() {

    override val container = container<BugReportState, BugReportSideEffect>(BugReportState())

    internal fun setTitle(
        title: String,
    ) = intent {
        reduce {
            state.copy(
                title = title,
            )
        }
    }

    internal fun setContent(
        content: String,
    ) = intent {
        reduce {
            state.copy(
                content = content,
            )
        }
    }

    internal fun setPosition(
        position: String,
    ) = intent {
        runCatching {
            reduce {
                state.copy(
                    selectedPosition = Position.valueOf(position),
                )
            }
        }.onFailure {
            reduce {
                state.copy(
                    selectedPosition = Position.All,
                )
            }
        }
    }

    internal fun setUriList(
        uriList: List<Uri>,
    ) = intent {
        reduce {
            state.copy(
                uriList = uriList,
            )
        }
    }
}