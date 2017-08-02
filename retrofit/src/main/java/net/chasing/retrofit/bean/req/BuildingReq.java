package net.chasing.retrofit.bean.req;

import net.chasing.retrofit.bean.base.BaseReq;

/**
 * Created by Chasing on 2017/6/9.
 */
public class BuildingReq extends BaseReq {
    private int cityId;
    private String name;

    public BuildingReq() {
        super(10106);
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
