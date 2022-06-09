package com.example.imagesearchapp.core


object CoreLib {
    var eventDispatcher: EventDispatcher
    init {
        eventDispatcher = EventDispatcher()
    }

        fun getDispatcher(): EventDispatcher? {
            return eventDispatcher
        }


}