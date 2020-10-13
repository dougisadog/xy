package com.doug.paylib.util

import java.util.*

class WepayRequest {

    var prepayid: String = ""
    var noncestr = UUID.randomUUID().toString().replace("-", "")
    var timestamp = Calendar.getInstance().timeInMillis.toString().substring(0, 10)
}