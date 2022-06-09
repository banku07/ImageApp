package com.example.imagesearchapp.core

import java.util.HashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Rajesh CP on 8/9/16.
 * The class responsible for dispatching the event to the
 * observers
 */
class EventDispatcher : Dispatcher<EventListner?> {
    /**
     * The map in which all the listeners will be saved
     */
    private val mListenersMap: HashMap<String, CopyOnWriteArrayList<EventListner>?>


    /**
     * Constructs a new instance of `Object`.
     */
    init {
        mListenersMap = HashMap<String, CopyOnWriteArrayList<EventListner>?>()
    }

    override fun addEventListener(type: String?, listener: EventListner?) {
        synchronized(mListenersMap) {
            var listenerList: CopyOnWriteArrayList<EventListner>? = mListenersMap[type]
            if (listenerList == null) {
                listenerList = CopyOnWriteArrayList<EventListner>()
                mListenersMap[type!!] = listenerList
            }
            if (!hasEventListener(type, listener)) {
                listenerList!!.add(listener)
            }
        }
    }

    override fun removeEventListener(type: String?, listener: EventListner?) {
        synchronized(mListenersMap) {
            val listeners: CopyOnWriteArrayList<EventListner>? =
                mListenersMap[type]
            if (listeners == null) {
//                AppLogger.debug(
//                    "Invalid call to remove $type for",
//                    listener.getClass().getName()
//                )
                return
            }
            listeners.remove(listener)
//            AppLogger.debug("Stopped listening for $type", listener.getClass().getName())
            if (listeners.size == 0) {
                mListenersMap.remove(type)
            }
        }
    }

    override fun hasEventListener(type: String?, listener: EventListner?): Boolean {
        synchronized(mListenersMap) {
            val listeners =
                mListenersMap[type] ?: return false
            return listeners.contains(listener)
        }
    }

    override fun dispatchEvent(event: Event?) {
        if (event == null) {
//            AppLogger.debug(
//                com.corelib.arvind.events.EventDispatcher.TAG,
//                "Event object can not be null"
//            )
            return
        }
        val type: String? = event.type
        event.source= this
        val listeners: CopyOnWriteArrayList<EventListner>?
        synchronized(mListenersMap) { listeners = mListenersMap[type] }
        if (listeners == null) return
        for (listener in listeners) {
            listener.onEvent(event)
        }
    }

    override fun dumb() {
        synchronized(mListenersMap) { mListenersMap.clear() }
    }

}