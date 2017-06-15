package net.mapout.retrofit.bean.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chasing on 2017/6/9.
 */
public class Response<T> {

    /**
     * cmd : 10001
     * items : [{"address":"广州市越秀区东风东路838号(东峻广场右侧)","buildingTypeName":"酒店","dis":1986,"hot":0,"lat":23.137414,"lon":113.314315,"name":"七天酒店（杨箕地铁站店）","siteAreaName":"越秀区","uuid":"11e5-a9f0-c3cf5ce2-a57b-15824c8fc7b3"}]
     * model : building
     * resultCode : 0
     * resultMsg : 请求处理成功
     * totalNum : 17
     */

    private int cmd;
    private String model;
    private int resultCode;
    private String resultMsg;
    private int totalNum;
    private List<T> items;
    private String session;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getItems() {
        if (items == null) return new ArrayList<>();
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
