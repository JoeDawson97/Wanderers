package com.wanderers.wanderers.app.model;

import com.wanderers.wanderers.sys.base.BaseModel;
import lombok.Data;

@Data
public class Musician extends BaseModel{

    private String mscnName;
    private String mscnHome;
    private String mscnAvatar;
    private String mscnIntr;
    private int mscnFollows;
    private int mscnViews;
    private int mscnListens;
    private int mscnDownloads;

    private Band band;

}
