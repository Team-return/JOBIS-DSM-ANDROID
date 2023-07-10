package team.retum.jobis_android.viewmodel.bugreport

import android.net.Uri
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.jobis_android.contract.bugreport.BugReportSideEffect
import team.retum.jobis_android.contract.bugreport.BugReportState
import team.retum.jobis_android.contract.bugreport.Position
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

internal class BugReportViewModel @Inject constructor(


): BaseViewModel<BugReportState, BugReportSideEffect>(){

    override fun sendEvent(event: Event) {}

    override val container = container<BugReportState, BugReportSideEffect>(BugReportState())

    private val mutableUriList = mutableListOf<Uri>()

    internal fun setTitle(
        title: String,
    ) = intent{
        reduce{
            state.copy(
                title = title,
            )
        }
    }

    internal fun setContent(
        content: String,
    ) = intent{
        reduce{
            state.copy(
                content = content,
            )
        }
    }

    internal fun addUri(
        uri: Uri?,
    ) = intent{
        if(uri != null) {
            mutableUriList.add(uri)
            reduce {
                state.copy(
                    uriList = mutableUriList,
                )
            }
        }
    }

    internal fun removeUri(
        index: Int,
    ) = intent{
        mutableUriList.removeAt(index)
        reduce {
            state.copy(
                uriList = mutableUriList,
            )
        }
    }

    internal fun setPosition(
        position: String,
    ) = intent{
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
}