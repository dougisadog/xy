package com.doug.paylib.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.alipay.sdk.app.PayTask
import com.doug.paylib.alipay.PayResult
import com.doug.paylib.alipay.SignUtils
import com.doug.paylib.alipay.ZfbPayResult
import com.doug.paylib.alipay.util.OrderInfoUtil2_0
import java.io.UnsupportedEncodingException
import java.math.BigDecimal
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


class AlipayManager {

    companion object {
        const val TAG = " AlipayManager"

        private var mInstance: AlipayManager? = null
            get() {
                if (null == field) {
                    field = AlipayManager()
                }
                return field
            }

        @JvmStatic
        @Synchronized
        fun getInstance(): AlipayManager {
            return requireNotNull(mInstance)
        }

        var APPID = ""
        var RSA2_PRIVATE = ""
        var RSA_PRIVATE = ""

        //全路径
        var ALI_NOTIFY_URL = ""
        var PARTNER = ""
        var SELLER = ""
        var isV2 = true

        fun initV2(appId: String, rsa2: String, ali_notify_url: String) {
            isV2 = true
            APPID = appId
            RSA2_PRIVATE = rsa2
            ALI_NOTIFY_URL = ali_notify_url
        }

        fun initV1(partner: String, seller: String, ali_notify_url: String) {
            isV2 = false
            ALI_NOTIFY_URL = ali_notify_url
            PARTNER = partner
            SELLER = seller
        }


    }

    private val mHandler: Handler = Handler(Looper.getMainLooper())

    fun pay(activity: Activity, alipayRequest: AlipayRequest, payCallback: PayCallback) {
        if (isV2) {
            payV2(activity, alipayRequest, payCallback)
        }
        else {
            payV1(activity, alipayRequest, payCallback)

        }
    }

    /**
     * 支付宝支付业务示例
     */
    private fun payV2(activity: Activity, alipayRequest: AlipayRequest, payCallback: PayCallback) {
        /*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * orderInfo 的获取必须来自服务端；
		 */
        val rsa2 = RSA2_PRIVATE.isNotEmpty()
//        val params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2)
        val params =
            buildOrderParamMap(
                alipayRequest.price,
                alipayRequest.title,
                alipayRequest.body,
                alipayRequest.tradeNo
            )
        val orderParam = OrderInfoUtil2_0.buildOrderParam(params)
        val privateKey = if (rsa2) RSA2_PRIVATE else RSA_PRIVATE
        val sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2)
        val orderInfo = "$orderParam&$sign"
        val payRunnable = Runnable {
            val alipay = PayTask(activity)
            val result = alipay.payV2(orderInfo, true)
            handleResultV2(result, payCallback)
        }
        // 必须异步调用
        Thread(payRunnable).start()
    }


    private fun handleResultV2(result: Map<String, String>, payCallback: PayCallback) {
        //            Log.i("msp", result.toString())
        val finalResult = PayResult(result)
        Log.i("alipay", finalResult.toString())
        mHandler.post {
            if (finalResult.resultStatus == "9000") {
                payCallback.onSuccess()
            } else {
                payCallback.onFailed(finalResult.memo)
            }
        }
    }

    /**
     * 构造支付订单参数列表
     */
    private fun buildOrderParamMap(
        price: Double,
        title: String,
        body: String,
        tradeNo: String,
        rsa2: Boolean = RSA2_PRIVATE.isNotEmpty()
    ): Map<String, String> {
        val keyValues = hashMapOf<String, String>()

        keyValues["app_id"] = APPID

        val priceText = BigDecimal(price).setScale(2).toString()
        keyValues["biz_content"] =
            "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"${priceText}\",\"subject\":\"${title}\",\"body\":\"${body}\",\"out_trade_no\":\"" + tradeNo + "\"}"

        keyValues["charset"] = "utf-8"
        keyValues["method"] = "alipay.trade.app.pay"
        keyValues["sign_type"] = if (rsa2) "RSA2" else "RSA"
        keyValues["notify_url"] = ALI_NOTIFY_URL

        val date = Calendar.getInstance().time
        val strDateFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(strDateFormat)
//        keyValues["timestamp"] = "2016-07-29 16:55:53"
        keyValues["timestamp"] = sdf.format(date)
        keyValues["version"] = "1.0"

        return keyValues
    }

    private fun payV1(activity: Activity, alipayRequest: AlipayRequest, payCallback: PayCallback) {
        val price = BigDecimal(alipayRequest.price).setScale(2).toString()
        val orderInfo = getOrderInfo(
            alipayRequest.title,
            alipayRequest.body,
            price,
            alipayRequest.tradeNo
        )
        var sign = signV1(orderInfo)
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        // 完整的符合支付宝参数规范的订单信息
        val payInfo: String = (orderInfo.toString() + "&sign=\"" + sign + "\"&"
                + getSignType())

        val payRunnable = Runnable { // 构造PayTask 对象
            val alipay = PayTask(activity)
            // 调用支付接口，获取支付结果
            val result = alipay.pay(payInfo, true)
            val zfbPayResult = ZfbPayResult(result)
            handleResult(zfbPayResult, payCallback)
        }

        // 必须异步调用
        val payThread = Thread(payRunnable).start()
    }

    fun handleResult(zfbPayResult: ZfbPayResult, callback: PayCallback) {
        val resultStatus: String = zfbPayResult.getResultStatus()
        val code = Integer.valueOf(resultStatus)

        mHandler.post {
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档

            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
//            requestPayData(code)
                //支付成功
                // 判断resultStatus 为非“9000”则代表可能支付失败
                // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                callback.onSuccess()
            } else if (TextUtils.equals(resultStatus, "8000")) {
//            requestPayData(code)
                //支付结果确认中
            } else {
                //支付失败
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                callback.onFailed(zfbPayResult.toString())
            }
        }
    }

    fun signV1(content: String?): String? {
        return SignUtils.sign(content, RSA_PRIVATE, false)
    }

    fun getSignType(): String? {
        return "sign_type=\"RSA\""
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    fun getOrderInfo(subject: String, body: String, price: String, orderNo: String): String? {
        // 签约合作者身份ID
        var orderInfo = "partner=" + "\"" + PARTNER + "\""

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\""

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=\"$orderNo\""

        // 商品名称
        orderInfo += "&subject=\"$subject\""

        // 商品详情
        orderInfo += "&body=\"$body\""

        // 商品金额
        orderInfo += "&total_fee=\"$price\""

        // 服务器异步通知页面路径    经过了截取路径的操作
        orderInfo += ("&notify_url=" + "\"" + ALI_NOTIFY_URL
                + "\"")

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\""

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\""

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\""

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\""

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\""

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        return orderInfo
    }

}