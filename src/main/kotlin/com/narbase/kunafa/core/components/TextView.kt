@file:Suppress("unused")

package com.narbase.kunafa.core.components


/**
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2013] - [2018] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 10/9/17.
 */
open class TextView(parent: View? = null) : HtmlView(parent) {

    var text
        get() = element.innerHTML
        set(value) {
            element.innerHTML = value
        }
}