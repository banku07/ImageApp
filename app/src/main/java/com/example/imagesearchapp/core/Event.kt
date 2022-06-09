package com.example.imagesearchapp.core
/**
 * Common interface used to enable the event primitives
 */
interface Event {
    /**
     * Function return the type of event
     * @return of type [String]
     */
    val type: String?
    /**
     * To get the creator or source of the event
     * @return of type [Object]
     */
    /**
     * To set the source of the event propagation
     * @param object the object from which the event is triggered
     */
    var source: Any?

    /**
     * function check event dispatched with bundle or not
     *
     * @return true if exist else flase
     */
    fun hasExtra(): Boolean
}