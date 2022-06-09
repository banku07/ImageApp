package com.example.imagesearchapp.core

import android.os.Bundle

/**.
 * Class act as the bas class for all the vents in the
 * System
 */
 class CoreEvent(
   override var type: String,
   protected val mExtra: Bundle?
) : Event {


    /**
     * getter function for the `mExtra`
     *
     * @return the `mExtra`
     */
    fun getmExtra(): Bundle? {
        return mExtra
    }

    /**
     * function check event dispatched with bundle or not
     *
     * @return true if exist else flase
     */
   override fun hasExtra(): Boolean {
        return mExtra != null
    }
    /**
     * Event propagation source
     */
     override var source: Any? = null

    fun setmType(mType: String) {
        type = mType
    }

}