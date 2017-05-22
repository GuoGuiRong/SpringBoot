package com.ggr.highlight_springmvc4.domain;

/**
 * Created by GuiRunning on 2017/5/22.
 */
public class DemoObj
{
    private long id;
    private String name;

    @Override
    public String toString() {
        return "DemoObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public DemoObj(){
        super();
    }
    public DemoObj(long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
