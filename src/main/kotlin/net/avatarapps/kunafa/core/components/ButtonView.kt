package net.avatarapps.kunafa.core.components

import net.avatarapps.kunafa.core.components.layout.Container
import net.avatarapps.kunafa.core.dimensions.CalculatedDimension
import net.avatarapps.kunafa.core.dimensions.Dimension
import net.avatarapps.kunafa.core.dimensions.DynamicDimension
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document

/**
 * AVATAR APPS CONFIDENTIAL
 * ______________________________
 * [2013] - [2017] Avatar Apps
 * All Rights Reserved.
 * Created by islam
 * On: 10/15/17.
 */
class ButtonView(parent: Container? = null) : View(parent) {
    val button by lazy {
        element.hidden = true
        val b = document.createElement("button") as HTMLButtonElement
        element.append(b)
        return@lazy b
    }

    override fun updateContentWidth() {
        super.updateContentWidth()
        if (width is DynamicDimension) {
            (width as? DynamicDimension)?.configure(button, Dimension.Type.width)
        } else (width as? CalculatedDimension)?.let {
            button.style.width = "${it.pixels}px"
            button.style.minWidth = "${it.pixels}px"
        }
    }

    override fun updateContentHeight() {
        super.updateContentHeight()
        if (height is DynamicDimension) {
            (height as? DynamicDimension)?.configure(button, Dimension.Type.height)
        } else (height as? CalculatedDimension)?.let {
            button.style.height = "${it.pixels}px"
            button.style.minHeight = "${it.pixels}px"
        }



    }

}