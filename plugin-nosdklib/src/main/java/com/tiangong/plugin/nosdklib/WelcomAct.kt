package com.tiangong.plugin.nosdklib

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.qihoo360.replugin.RePlugin
import com.second.MCheck
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import com.zhy.http.okhttp.https.HttpsUtils
import com.zhy.http.okhttp.log.LoggerInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


/**
 * @author pengyanming
 * @date 2019/8/2.
 * Email：916193549@QQ.COM
 */
class WelcomAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_welcom)
        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(p0: String?, p1: SSLSession?): Boolean {
                        return true
                    }

                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build()

        OkHttpUtils.initClient(okHttpClient)
        val operatorType = MCheck.getSubscriptionOperatorType(this)
        if (operatorType != 0 || operatorType != -1) {
            Handler().postDelayed({
                Toast.makeText(application, "进马甲包", Toast.LENGTH_LONG).show()
            }, 400)
        } else {
            request()
        }

    }

    fun request() {
        OkHttpUtils
                .get()
                .url(" http://677029.com/getAppConfig.php?/")
                .addParams("appid", application.packageName)
                .build()
                .execute(object : StringCallback() {
                    override fun onResponse(response: String?, id: Int) {
                        val result = JSON.parseObject(response, MJ::class.java)
                        if (result != null && result.isSuccess) {
                            if (result.getUrl().equals("http://") || result.getUrl().equals("https://")) {
                                val startActivity = RePlugin.startActivity(applicationContext,
                                        RePlugin.createIntent("com.tiangong.android.plugin.demo",
                                                "com.tiangong.android.plugin.demo.MainActivity"))
                                if (startActivity) {
                                    Toast.makeText(application, "进SDK", Toast.LENGTH_LONG).show()
                                    finish()
                                } else {
                                    Toast.makeText(this@WelcomAct, "进入插件失败", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val intent = Intent(this@WelcomAct, WebActivity::class.java)
                                intent.putExtra("url", result.url)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(application, "进马甲包", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                        Toast.makeText(application, "网络异常", Toast.LENGTH_LONG).show()
                    }

                })

    }
}