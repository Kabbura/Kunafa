@file:Suppress("unused")

package com.narbase.kunafa.core.components


import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import kotlin.browser.document


/**
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2013] - [2018] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 10/31/17.
 */
class Radio(parent: View? = null) : View(parent) {

    override val element: HTMLInputElement = (document.createElement("input") as HTMLInputElement).apply {
        this.type = "radio"
    }

    var isChecked: Boolean
        get() = element.checked
        set(value) {
            element.checked = value

        }

    var onChange: ((Event) -> Unit)? = null
        set(value) {
            field = value
            element.onchange = value
        }

}
