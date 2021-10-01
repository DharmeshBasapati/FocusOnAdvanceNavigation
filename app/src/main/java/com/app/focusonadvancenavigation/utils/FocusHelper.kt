package com.app.focusonadvancenavigation.utils

import java.math.RoundingMode

class FocusHelper {
    companion object {
        fun convertToTwoDecimalPoints(valueInDouble: Double) =
            valueInDouble.toBigDecimal().setScale(2, RoundingMode.UP)
                .toDouble()
    }
}