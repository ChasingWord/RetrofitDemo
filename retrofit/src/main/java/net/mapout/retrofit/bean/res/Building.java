package net.mapout.retrofit.bean.res;

/**
 * Created by Chasing on 2017/6/9.
 */
public class Building {
    private long id;
    private String name;
    private String address;
    private int pathCount;
    private String logo;
    private int locked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPathCount() {
        return pathCount;
    }

    public void setPathCount(int pathCount) {
        this.pathCount = pathCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}
