package com.example.demo.controller;

import com.example.demo.dto.Comment;
import com.example.demo.dto.Post;
import com.example.demo.dto.User;
import com.example.demo.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ApiController {
    private final GatewayService gatewayService;

    public ApiController(final GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = gatewayService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public final ResponseEntity<List<Post>> getAllPosts() {
        List<Post> postList = gatewayService.getAllPosts();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public final ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> commentList = gatewayService.getAllComments();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}/posts")
    public final ResponseEntity<List<Post>> getPostsOfUser(
            @PathVariable("user_id") final int userId) {
        List<Post> postList = gatewayService.getAllPosts();
        List<Post> filteredPostList =
                postList.stream().filter(post -> post.getUser_id() == userId).collect(Collectors.toList());
        return new ResponseEntity<>(filteredPostList, HttpStatus.OK);
    }

    @GetMapping("/post/{post_id}/comments")
    public final ResponseEntity<List<Comment>> getCommentsOfPosts(
            @PathVariable("post_id") final int postId) {
        List<Comment> commentList = gatewayService.getAllComments();
        List<Comment> filteredCommentList =
                commentList.stream()
                        .filter(comment -> comment.getPost_id() == postId)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(filteredCommentList, HttpStatus.OK);
    }
}
