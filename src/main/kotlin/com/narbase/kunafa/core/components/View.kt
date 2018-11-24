package com.narbase.kunafa.core.components

import com.narbase.kunafa.core.components.layout.Alignment
import com.narbase.kunafa.core.components.layout.Container
import com.narbase.kunafa.core.css.ClassNameGenerator
import com.narbase.kunafa.core.css.ClassSelector
import com.narbase.kunafa.core.css.RuleSet
import com.narbase.kunafa.core.dimensions.Dimension
import com.narbase.kunafa.core.dimensions.DynamicDimension
import com.narbase.kunafa.core.dimensions.LinearDimension
import com.narbase.kunafa.core.dimensions.dependent.wrapContent
import com.narbase.kunafa.core.dimensions.px
import com.narbase.kunafa.core.drawable.Color
import com.narbase.kunafa.core.presenter.ViewController
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.css.CSSStyleSheet
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable

/**
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2013] - [2018] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 9/30/17.
 */
open class View(var parent: Container? = null) {
    var id: String? = null
        set(value) {
            field = value
            value?.let {
                element.id = it
            }
        }

    open val element: HTMLElement = document.createElement("div") as HTMLDivElement

    var viewController: ViewController? = null

    private fun updateElementDimensions() {
        updateElementWidth()
        updateElementHeight()
    }

    open fun updateElementWidth() {
        if (width is DynamicDimension) {
//            (width as? DynamicDimension)?.configure(element, Dimension.Type.width)
        } else (width as? LinearDimension)?.let {
            if (element.style.width == it.toString()) return
            element.style.width = it.toString()
            element.style.minWidth = it.toString()
            onResizedListeners.forEach { it.second() }
        }
        updateContentWidth()
    }

    internal open fun updateContentWidth() {

    }

    private var savedDisplayState: String? = "null"

    var isVisible: Boolean = true
        set(value) {
            field = value
            when (value) {
                true -> {
                    if (element.style.display == "none")
                        element.style.display = savedDisplayState ?: "block"

                }
                false -> {
                    if (element.style.display != "none") {
                        savedDisplayState = element.style.display
                        element.style.display = "none"
                    }
                }
            }


        }

    open fun updateElementHeight() {
        if (height is DynamicDimension) {
//            (height as? DynamicDimension)?.configure(element, Dimension.Type.height)
        } else (height as? LinearDimension)?.let {
            if (element.style.height == it.toString()) return
            element.style.height = it.toString()
            element.style.minHeight = it.toString()
            onResizedListeners.forEach { it.second() }
        }
        updateContentHeight()
    }

    open internal fun updateContentHeight() {

    }

    open var onClick: ((Event) -> Unit)? = null
        set(value) {
            field = value
            element.onclick = value
        }


    open var width: Dimension = wrapContent
        set(value) {
            field = value
            updateElementDimensions()
        }

    open var height: Dimension = wrapContent
        set(value) {
            field = value
            updateElementDimensions()
        }

    open val wrappedContentWidth: LinearDimension
        get() = throw WrapContentNotSupportedException("WrapContent not supported in View")

    open val wrappedContentHeight: LinearDimension
        get() = throw WrapContentNotSupportedException("WrapContent not supported in View")

    var backgroundColor: Color by observable(Color()) { _, _, _ ->
        element.style.backgroundColor = backgroundColor.toCss()
    }

    fun setMargin(margin: LinearDimension) {
        marginTop = margin
        marginStart = margin
        marginEnd = margin
        marginBottom = margin
    }

    var marginTop: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.marginTop = newValue.toString()
    }

    var marginStart: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.marginLeft = newValue.toString()
    }

    var marginEnd: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.marginRight = newValue.toString()
    }

    var marginBottom: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.marginBottom = newValue.toString()
    }

    fun setPadding(padding: LinearDimension) {
        paddingTop = padding
        paddingStart = padding
        paddingEnd = padding
        paddingBottom = padding
    }

    var paddingTop: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.paddingTop = newValue.toString()
    }

    var paddingStart: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.paddingLeft = newValue.toString()
    }

    var paddingEnd: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.paddingRight = newValue.toString()
    }

    var paddingBottom: LinearDimension by observable(0.px) { _, _, newValue ->
        //        element.style.paddingBottom = newValue.toString()
    }

    var isScrollableHorizontally by Delegates.observable(false) { _, _, isScrollable ->
        element.style.overflowX = if (isScrollable) "auto" else "visible"
        if (isScrollable && element.style.minWidth.isBlank()) {
            element.style.minWidth = "0"
        }
    }
    var isScrollableVertically by Delegates.observable(false) { _, _, isScrollable ->
        element.style.overflowY = if (isScrollable) "auto" else "visible"
        if (isScrollable && element.style.minHeight.isBlank()) {
            element.style.minHeight = "0"
        }
    }

    open fun configureElement() {
        element.style.boxSizing = "border-box"
        setMargin(0.px)
        setPadding(0.px)
    }

    fun <V : View> V.visit(rules: (RuleSet.() -> Unit)?, setup: V.() -> Unit): V {
        configureElement()
        this.setupStyleSheet(rules)
        this.addToParent()
        this.setup()
        this.viewController?.onViewCreated(this)
        return this
    }

    private fun <V : View> V.setupStyleSheet(rules: (RuleSet.() -> Unit)?) {
        if (rules == null) return
        val className = ClassNameGenerator.getClassName()
        val selector = ClassSelector(className)
        val ruleSet = RuleSet(selector).apply { rules() }
        val sheetElement = document.createElement("style") as HTMLStyleElement
        document.head?.appendChild(sheetElement)
        val sheet = sheetElement.sheet as? CSSStyleSheet
        ruleSet.toRulesList().forEach {
            console.log(it.toString())
            sheet?.insertRule(it.toString(), sheet.cssRules.length)
        }
        this.element.addClass(className)
    }


    internal open fun addToParent() {
        val validParent = parent ?: throw ParentNotFoundException()
        validParent.addChild(this)
        validParent.addOnResizedListener(this, this::onParentResized)
        addOnResizedListener(validParent, validParent::onChildrenResized)
    }


    protected val onResizedListeners: ArrayList<Pair<View, () -> Unit>> = arrayListOf()
    protected val onChildrenResizedListeners: ArrayList<Pair<View, () -> Unit>> = arrayListOf()
    protected val onParentResizedListeners: ArrayList<Pair<View, () -> Unit>> = arrayListOf()

    fun addOnResizedListener(listener: View, onResizeListener: () -> Unit) {
        onResizedListeners.add(Pair(listener, onResizeListener))
    }

    fun addOnParentResizedListener(listener: View, onResizeListener: () -> Unit) {
        onParentResizedListeners.add(Pair(listener, onResizeListener))
    }

    fun addOnChildrenResizedListener(listener: View, onResizeListener: () -> Unit) {
        onChildrenResizedListeners.add(Pair(listener, onResizeListener))
    }

    private fun onParentResized() {
        onParentResizedListeners.forEach { it.second() }
    }

    internal fun onChildrenResized() {
        onChildrenResizedListeners.forEach { it.second() }
    }

    var alignSelf: Alignment = Alignment.Start
        set(value) {
            element.style.alignSelf = value.cssName
        }

}


class ParentNotFoundException : Exception()

class DimensionNotAvailableOnViewException : Exception()

class WrapContentNotSupportedException(message: String) : Exception(message)
