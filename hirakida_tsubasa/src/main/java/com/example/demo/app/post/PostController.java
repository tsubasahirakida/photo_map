package com.example.demo.app.post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.app.comment.CommentForm;
import com.example.demo.app.login.LoginUserDetails;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.service.comment.CommentService;
import com.example.demo.service.like.LikeService;
import com.example.demo.service.post.PostService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/posts")
public class PostController {
    /*postServiceをフィールドとして持つ理由
    →PostServiceで定義されているメソッドを呼び出すため。
    　なぜメソッドを呼び出せるのか
    →インスタンス化されたオブジェクトをフィールドとして持つと、そのオブジェクトの機能も使うことができる
    　(イメージとしては、フィールド内のインスタンスを覆っているイメージ？？)
    */
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

    /*コンストラクタでセッター化している理由
    →
     */
    public PostController(PostService postService, LikeService likeService, CommentService commentService) {
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    @GetMapping
    public String index(Model model) {
        List<Post> postList = postService.getAll();
        model.addAttribute("postList", postList);
        // Authenticationクラスは認証されたユーザーの情報が格納されている。
        // SecurityContextHolder.getContext().getAuthentication()で取得可能
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            LoginUserDetails principal = (LoginUserDetails) auth.getPrincipal();
            List<Integer> myLikes = likeService.findMyLikes(principal.getUser().getId());
            model.addAttribute("myLikes", myLikes);
            model.addAttribute("userId", principal.getUser().getId());
        }
        return "posts/index";
    }

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "posts/new";
    }

    @PostMapping("/new")
    public String createPost(@RequestParam("image_url") MultipartFile file,
            @Validated PostForm postForm,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal LoginUserDetails userDetails) throws IOException, InterruptedException {

        if (result.hasErrors()) {
            return "posts/new";
        }

        Post post = new Post();
        post.setBody(postForm.getBody());
        post.setPlace(postForm.getPlace());
        post.setUser_id(userDetails.getUser().getId());
        
        // if (!postForm.getImage_url().isEmpty()) {
        // String base64Image =
        // Base64.encodeBase64String(postForm.getImage_url().getBytes());
        // post.setImage_url(base64Image);
        // }

        try {
            // 一時的な場所にファイルを保存する
            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);

            // サーバーの指定された場所にファイルを移動する
            String originalFileName = file.getOriginalFilename();
            // String hashedFileName = HashUtils.hashFileName(originalFileName);
            Path filePath = Paths.get("hirakida_tsubasa/src/main/resources/static/images/" + originalFileName);
            Files.move(tempFile.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Thread.sleep(500);

            // ファイルのパスをデータベースに保存する
            post.setImage_url(filePath.toString());
            postService.save(post);
            return "redirect:/posts";
        } catch (IOException e) {
            e.printStackTrace();
            return "posts/new";
        } catch (InterruptedException i) {
            i.printStackTrace();
            return "posts/new";
        }

        // postService.save(post);
        // redirectAttributes.addFlashAttribute("complete", "Completed!");
        // return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String showPostDetail(@PathVariable("id") int postId, Model model, @RequestParam(required = false) String error) {
        Post post = postService.show(postId);
        model.addAttribute("post", post);

        String postImage_url = post.getImage_url();
        String relative_path = "/images" + postImage_url.split("images")[1];
        model.addAttribute("postImage", relative_path);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            LoginUserDetails principal = (LoginUserDetails) auth.getPrincipal();
            List<Integer> myLikes = likeService.findMyLikes(principal.getUser().getId());
            model.addAttribute("myLikes", myLikes);
            model.addAttribute("userId", principal.getUser().getId());
        }

        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("error", error);

        List<Comment> commentList = commentService.getAll(postId);
        if (commentList.size() > 0) {
            model.addAttribute("commentTitle", "コメント一覧");
            model.addAttribute("commentList", commentList);
          }
        return "posts/show";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") int id, Model model,
            @AuthenticationPrincipal LoginUserDetails userDetails) {

        Post post = postService.show(id);

        if (post.getUser_id() != userDetails.getUser().getId()) {
            return "error/403";
        }

        PostForm postForm = new PostForm();
        postForm.setBody(post.getBody());
        postForm.setPlace(post.getPlace());

        String postImage_url = post.getImage_url();
        String relative_path = "/images" + postImage_url.split("images")[1];
        // postForm.setImage_url(post.getImage_url());

        model.addAttribute("postId", id);
        model.addAttribute("postForm", postForm);
        model.addAttribute("postImage", relative_path);
        return "posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(
            @RequestParam("image_url") MultipartFile file,
            @Validated PostForm postForm,
            BindingResult bindingResult,
            @RequestParam("postId") int id,
            @RequestParam("postImage") String image,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", id);
            model.addAttribute("postImage", image);
            return "/posts/edit";
        }

        Post post = new Post();
        if (id != 0) {
            post.setId(id);
        }
        post.setBody(postForm.getBody());
        post.setPlace(postForm.getPlace());
        // post.setImage_url(postForm.getImage_url());

        // postService.update(post);
        // return "redirect:/posts/" + id;
        try {
            // 一時的な場所にファイルを保存する
            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);

            // サーバーの指定された場所にファイルを移動する
            String originalFileName = file.getOriginalFilename();
            // String hashedFileName = HashUtils.hashFileName(originalFileName);
            Path filePath = Paths.get("hirakida_tsubasa/src/main/resources/static/images/" + originalFileName);
            Files.move(tempFile.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Thread.sleep(500);

            // ファイルのパスをデータベースに保存する
            post.setImage_url(filePath.toString());

            postService.update(post);
            return "redirect:/posts/" + id;
        } catch (IOException e) {
            System.out.println(image);
            String originalFilePath = image;
            Path concatenationFilePath = Paths.get("src/main/resources/static" + originalFilePath);
            // ファイルのパスをデータベースに保存する
            post.setImage_url(concatenationFilePath.toString());
            postService.update(post);
            return "redirect:/posts/" + id;
        } catch (InterruptedException i) {
            i.printStackTrace();
            return "/posts/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}