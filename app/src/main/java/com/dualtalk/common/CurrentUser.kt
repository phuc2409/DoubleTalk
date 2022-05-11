package com.dualtalk.common

class CurrentUser {
    companion object {
        var id: String = ""
        var email: String = ""
        var fullName: String = ""
        var imgUrl: String = ""

        /**
         * Id người nhận
         */
        const val receiveId: String = "2"
        const val receiveName: String = "Đạt"
    }
}