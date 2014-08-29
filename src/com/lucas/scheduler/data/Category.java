package com.lucas.scheduler.data;

import java.util.ArrayList;

public class Category extends AbstractParam{
    public int id = -1;
    public int defaultIntentId = -1;
    public ArrayList<Param> kinds = new ArrayList<Param>();
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
