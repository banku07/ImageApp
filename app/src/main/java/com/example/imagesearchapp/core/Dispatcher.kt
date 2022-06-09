package com.example.imagesearchapp.core

interface Dispatcher<D : EventListner?> {
    /**
     * Add the listener to the listener map
     *
     * @param type the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch `EventListener`
     */
    fun addEventListener(type: String?, listener: D)

    /**
     * Remove the listener for the listener map
     *
     * @param type the type of the event against which the listener to be removed
     * @param listener the listener to be removed
     */
    fun removeEventListener(type: String?, listener: D)

    /**
     * Check the listener already registered or not
     *
     * @param type type of the event against the existence to be checked
     * @param listener the listener to be mapped
     * @return returns true if mapped else false
     */
    fun hasEventListener(type: String?, listener: D): Boolean

    /**
     * The method notify the proper event listeners [EventListener.onEvent]
     * @param event to be propagated to the listener
     */
    fun dispatchEvent(event: Event?)

    /**
     * Function destroy the dispatcher
     */
    fun dumb()
}