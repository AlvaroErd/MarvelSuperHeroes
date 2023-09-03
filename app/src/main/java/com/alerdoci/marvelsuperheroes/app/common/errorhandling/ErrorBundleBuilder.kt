package com.alerdoci.marvelsuperheroes.app.common.errorhandling

import com.alerdoci.marvelsuperheroes.app.common.errorhandling.AppAction
import com.alerdoci.marvelsuperheroes.app.common.errorhandling.ErrorBundle

interface ErrorBundleBuilder {

    fun build(throwable: Throwable, appAction: AppAction): ErrorBundle
}
