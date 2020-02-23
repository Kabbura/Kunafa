package com.narbase.kunafa.core.routing

import kotlin.browser.window

/**
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2017] -[2019] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 2019/03/09.
 */

@Suppress("unused")
object Router {
    init {
        window.onpopstate = {
            update()
        }
    }

    var currentPath = "/"
    var parentRoute: Route? = null
    private val matchedRoutes = mutableSetOf<Route>()
    private var isUpdating = false
    private val rootRoutes = mutableListOf<Route>()
    private const val MAX_REDIRECT_LIMIT = 100

    fun onRouteMatch(route: Route) {
        matchedRoutes.add(route)
    }

    fun onRouteUnMatch(route: Route) {
        matchedRoutes.remove(route)
    }

    fun invalidateCache() {
        rootRoutes.clear()
    }

    @Suppress("LiftReturnOrAssignment")
    private fun update() {
        isUpdating = true
        var shouldRetry: Boolean
        var redirectCounter = 0
        do {
            try {
                rootRoutes.forEach { route ->
                    route.update()
                }
                shouldRetry = false
            } catch (e: RedirectException) {
                redirectCounter++
                if (redirectCounter < MAX_REDIRECT_LIMIT) {
                    shouldRetry = true
                } else {
                    shouldRetry = false
                    console.log("Maximum redirect limit 100 is reached. Please check if you are redirecting correctly.")
                }
            }
        } while (shouldRetry)
        isUpdating = false
    }

    fun add(route: Route) {
        rootRoutes.add(route)
    }


    fun navigateTo(path: String) {
        val shouldPrevent = matchedRoutes.firstOrNull { it.meta.onRouteWillChange?.invoke() == false } != null
        if (shouldPrevent) return
        window.history.pushState(null, "", "/${path.trimStart('/')}")
        if (isUpdating) throw RedirectException()
        else update()
    }

}