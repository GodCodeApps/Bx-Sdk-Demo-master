package com.second

import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.util.*


object MCheck {
    /**
     * 判断国家是否是国内用户
     * @return
     */
    fun isCN(context: Context): Boolean {
        var tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var countryIso = tm.simCountryIso
        var isCN = false//判断是不是大陆
        if (!TextUtils.isEmpty(countryIso)) {
            countryIso = countryIso.toUpperCase(Locale.US)
            if (countryIso.contains("CN")) {
                isCN = true
            }
        }
        return isCN
    }

    /**
     * 检查手机是否有sim卡
     */
     fun hasSim(context: Context): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val operator = tm.simOperator
        return !TextUtils.isEmpty(operator)
    }

    /**
     * 获取设备拨号运营商
     *
     * @return ["中国电信CTCC":3]["中国联通CUCC:2]["中国移动CMCC":1]["other":0]["无sim卡":-1]
     */
    fun getSubscriptionOperatorType(context: Context): Int {
        var opeType = -1
        // No sim
        if (!hasSim(context)) {
            return opeType
        }

        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val operator = tm.networkOperator
        // 中国联通
        if ("46001" == operator || "46006" == operator || "46009" == operator) {
            opeType = 2
            // 中国移动
        } else if ("46000" == operator || "46002" == operator || "46004" == operator || "46007" == operator) {
            opeType = 1
            // 中国电信
        } else if ("46003" == operator || "46005" == operator || "46011" == operator) {
            opeType = 3
        } else {
            opeType = 0
        }
        return opeType
    }
}