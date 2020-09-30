package com.doug.paylib.util

import android.content.Context
import android.util.Log
import com.doug.paylib.wepay.MD5
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory


class WepayManager {

    companion object {
        const val TAG = " WepayManager"

        private var mInstance: WepayManager? = null
            get() {
                if (null == field) {
                    field = WepayManager()
                }
                return field
            }

        @JvmStatic
        @Synchronized
        fun getInstance(): WepayManager {
            return requireNotNull(mInstance)
        }

        var APPID = ""
        var PARTNER_ID = ""
        var WX_PAY_KEY = ""


        fun init(context: Context, appId: String, partnerId: String, WX_PAY_KEY: String) {
            val msgApi = WXAPIFactory.createWXAPI(context, null)
            // 将该app注册到微信
//            msgApi.registerApp("wxd930ea5d5a258f4f")
            msgApi.registerApp(appId)
            getInstance().msgApi = msgApi
            APPID = appId
            PARTNER_ID = partnerId
        }
    }

    private lateinit var msgApi: IWXAPI

    var payCallback: PayCallback? = null

    fun pay(wepayRequest: WepayRequest, payCallback: PayCallback) {
        this.payCallback = payCallback
        val request = PayReq()
        request.appId = APPID
        request.partnerId = PARTNER_ID
        request.prepayId = wepayRequest.prepayid
        request.packageValue = "Sign=WXPay"
        request.nonceStr = wepayRequest.noncestr
        request.timeStamp = wepayRequest.timestamp

        val signParams: MutableList<Pair<String, String>> = mutableListOf()
        signParams.add(Pair("appid", request.appId))
        signParams.add(Pair("noncestr", request.nonceStr))
        signParams.add(Pair("package", request.packageValue))
        signParams.add(Pair("partnerid", request.partnerId))
        signParams.add(Pair("prepayid", request.prepayId))
        signParams.add(Pair("timestamp", request.timeStamp))
        request.sign = genAppSign(signParams)
        msgApi.sendReq(request)
    }


    /**
     * 生成微信支付数据的签名方法 ,是  genPayReq() 方法里面的子方法
     * @param params
     * @return
     */
    private fun genAppSign(params: List<Pair<String, String>>): String? {
        val sb = StringBuilder()
        for (i in params.indices) {
            sb.append(params[i].first)
            sb.append('=')
            sb.append(params[i].second)
            sb.append('&')
        }
        sb.append("key=")
        sb.append(WX_PAY_KEY)
        sb.append("sign str\n$sb\n\n")
        val appSign: String = MD5.getMessageDigest(sb.toString().toByteArray()).toUpperCase()
        Log.e("orion", appSign)
        return appSign
    }


}