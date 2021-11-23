package com.plateform.constant

object Constants {

    interface ERROR_TYPE {
        companion object {
            const val NO_INTERNET = 1
            const val CANCELLED = 2
            const val GENERIC = 0
        }
    }

    interface MEDIUM {
        companion object {
            const val FACEBOOK = "FACEBOOK"
            const val GOOGLE = "GOOGLE"
        }
    }


}