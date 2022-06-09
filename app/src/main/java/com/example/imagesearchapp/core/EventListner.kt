package com.example.imagesearchapp.core

import com.example.imagesearchapp.core.Event

interface EventListner {
    /**
     * Delegate method which will be called against respective events
     * @param event the event which is dispatched by the [Dispatcher]
     */
    fun onEvent(event: Event?)
}