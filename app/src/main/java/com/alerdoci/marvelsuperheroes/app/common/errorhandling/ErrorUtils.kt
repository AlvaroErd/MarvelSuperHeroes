package com.alerdoci.marvelsuperheroes.app.common.errorhandling

import android.content.Context
import com.alerdoci.marvelsuperheroes.R

object ErrorUtils {

    fun buildErrorMessageForDialog(context: Context, errorBundle: ErrorBundle): ErrorMessageForDialog {
        return ErrorMessageForDialog(
            context.getString(R.string.error_dialog_title),
            context.getString(errorBundle.stringId),
            errorBundle.debugMessage,
            errorBundle.appAction.code,
            errorBundle.appError.code
        )
    }
}
