package com.example.demo.app.like;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.app.login.LoginUserDetails;
import com.example.demo.entity.Like;
import com.example.demo.service.like.LikeService;

@Controller
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/create")
    @ResponseBody
    public void createLike(@RequestBody Map<String, Object> reqData,
        @AuthenticationPrincipal LoginUserDetails userDetails) {

        int postId = (Integer)reqData.get("postId");
        
        Like like = new Like();
        like.setPost_id(postId);
        like.setUser_id(userDetails.getUser().getId());
        likeService.createLike(like);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteLike(@RequestBody Map<String, Object> reqData,
            @AuthenticationPrincipal LoginUserDetails userDetails) {

        int postId = (Integer)reqData.get("postId");
        Like like = new Like();
        like.setPost_id(postId);
        like.setUser_id(userDetails.getUser().getId());
        likeService.deleteLike(like);
    }
}