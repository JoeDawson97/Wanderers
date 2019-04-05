package com.wanderers.wanderers.app.model;

import com.wanderers.wanderers.sys.base.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class Music extends BaseModel{
    private String mscName;
    private String mscAvatar;
    private String mscIntr;
    private int mscListens;
    private int mscDownloads;
    private int mscPrices;
    private List<Tag> tagList;

    private Album album;
    private Band band;
}
