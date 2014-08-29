package com.lucas.scheduler.data;

public class Kind extends AbstractParam{
    public int id = -1;
    public int defaultPresure = -1;
    public int defaultIntentId = -1;
    public int categoryId = -1;
    public String title;
    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return title;
    }
    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return id;
    }
}
