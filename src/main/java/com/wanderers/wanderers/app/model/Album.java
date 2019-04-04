package com.wanderers.wanderers.app.model;

import com.wanderers.wanderers.sys.base.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class Album extends BaseModel{

    private String albumName;
    private String albumAvatar;
    private String albumIntr;
    private int albumListens;
    private int albumDownloads;

    private List<Music> musicList;
    private Musician musician;
    private Band band;
}
