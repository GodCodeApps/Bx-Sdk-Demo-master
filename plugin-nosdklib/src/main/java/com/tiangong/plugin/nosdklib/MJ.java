package com.tiangong.plugin.nosdklib;

public class MJ {

    /**
     * success : false
     * msg : 没有找到appid对应的配置
     */

    private boolean success;
    private String msg;
private String ShowWeb;//0\"不跳转， \"1\"跳转
    private  String PushKey;
    private String Url;

    public String getShowWeb() {
        return ShowWeb;
    }

    public void setShowWeb(String showWeb) {
        ShowWeb = showWeb;
    }

    public String getPushKey() {
        return PushKey;
    }

    public void setPushKey(String pushKey) {
        PushKey = pushKey;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
