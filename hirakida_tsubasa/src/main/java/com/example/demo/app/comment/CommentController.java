package com.example.demo.app.comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.service.comment.CommentService;
import com.example.demo.service.post.PostService;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/new")
    public String createComment(@Validated CommentForm commentForm,
        BindingResult bindingResult,
        @RequestParam("postId") int postId,
        @RequestParam("userId") int userId,
        RedirectAttributes redirectAttributes,
        Model model){

            if (bindingResult.hasErrors()) {
                Post post = postService.show(postId);
                model.addAttribute("post", post);

                String postImage_url = post.getImage_url();
                String relative_path = "/images" + postImage_url.split("images")[1];
                model.addAttribute("postImage", relative_path);

                model.addAttribute("postId", postId);
                model.addAttribute("userId", userId);

                redirectAttributes.addAttribute("error", "true");

                return "redirect:/posts/" + postId;
            }
            Comment comment = new Comment();
            comment.setBody(commentForm.getBody());
            comment.setPost_id(postId);
            comment.setUser_id(userId);
            commentService.save(comment);
            return "redirect:/posts/" + postId;
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable("id") int commentId,
        @RequestParam("postId") int postId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}