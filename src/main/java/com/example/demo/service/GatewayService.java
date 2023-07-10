package com.example.demo.service;

import com.example.demo.dto.Comment;
import com.example.demo.dto.Post;
import com.example.demo.dto.User;

import java.util.List;

public interface GatewayService {

    List<User> getAllUsers();

    List<Post> getAllPosts();

    List<Comment> getAllComments();
}
