package com.wuyazhou.learn.listview.ImageLoadList;

import java.util.List;

/**
 * @author wuyzh
 * */
public class ItemInfos {
    public String status;
    public List<Item> data;
    public String msg;

    public class Item{
        public String id;
        public String name;
        public String picSmall;
        public String picBig;
        public String description;
        public String learner;
    }

}
