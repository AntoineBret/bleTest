package com.ab.utils

import android.app.Application
import com.ab.kotlinutils.ComponentHolder
import com.ab.kotlinutils.ComponentsMap

open class ComponentHolderApp : Application(), ComponentHolder by ComponentsMap()
