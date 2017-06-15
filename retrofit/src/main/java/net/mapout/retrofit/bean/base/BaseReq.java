package net.mapout.retrofit.bean.base;

import net.mapout.retrofit.util.MobileInfo;

import java.util.Map;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BaseReq {

    /**
     * pageIndex : 1
     * pageSize : 50
     * net : 1
     * cmd : 10001
     * platform : android
     * version : 4.0.0
     * model : MI-ONE Plus
     * imei : 8660-AAABQNDYA-109031
     */

    private int pageIndex = 1;
    private int pageSize = 20;
    private int net;
    private int cmd;
    private String platform;
    private String version;
    private String model;
    private String imei;
    private String sys;
    private long userId;
    private String session;

    public BaseReq(int cmd) {
        Map<String, Object> deviceInfo = MobileInfo.getInstance().getDeviceInfo();
        this.cmd = cmd;

        net = (int) deviceInfo.get(MobileInfo.NET_TYPE);
        version = (String) deviceInfo.get(MobileInfo.APP_VERSION);
        sys = (String) deviceInfo.get(MobileInfo.SYS_VERSION);
        model = (String) deviceInfo.get(MobileInfo.MODEL);
        platform = (String) deviceInfo.get(MobileInfo.PLATFORM);
        imei = (String) deviceInfo.get(MobileInfo.IMEI);
        userId = (long) deviceInfo.get(MobileInfo.USER_ID);
        session = (String) deviceInfo.get(MobileInfo.SESSION);
//        userId = 1;
//        session = "450338FC515A525C4014A1E713BBFF5A";
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
