package com.wanderers.wanderers.app.model;

import com.wanderers.wanderers.sys.base.BaseModel;
import lombok.Data;

import java.util.List;

@Data
public class Band extends BaseModel{
    private String bandName;
    private String bandAvatar;
    private String bandIntr;
    private int bandFollows;
    private int bandViews;
    private int bandListens;
    private int Downloads;

    private List<Musician> musicianList;
    private List<Music> musicList;
}
