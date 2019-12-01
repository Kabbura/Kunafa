package com.narbase.kunafa.core.components


import org.w3c.dom.HTMLButtonElement
import kotlin.browser.document

/**
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2013] - [2018] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 10/15/17.
 */
@Suppress("unused")
class Button(parent: View? = null) : HtmlView(parent) {
    override val element: HTMLButtonElement = document.createElement("button") as HTMLButtonElement
    var text: String?
        get() = element.textContent
        set(value) {
            element.textContent = value
        }
}