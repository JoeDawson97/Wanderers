package com.wanderers.wanderers.app.web;

import com.wanderers.wanderers.app.model.Music;
import com.wanderers.wanderers.app.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @RequestMapping("")
    public Music find(Music music){return musicService.find(music);}
}

