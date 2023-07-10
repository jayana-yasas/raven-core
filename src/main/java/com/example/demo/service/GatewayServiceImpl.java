package com.example.demo.service;

import com.example.demo.dto.Comment;
import com.example.demo.dto.Post;
import com.example.demo.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final RestTemplate restTemplate;

    @Override
    public List<User> getAllUsers() {
        User[] userList =
                restTemplate.getForObject("https://gorest.co.in/public/v2/users", User[].class);
        return Arrays.asList(userList);
    }

    @Override
    public List<Post> getAllPosts() {
        Post[] userList =
                restTemplate.getForObject("https://gorest.co.in/public/v2/posts", Post[].class);
        return Arrays.asList(userList);
    }

    @Override
    public List<Comment> getAllComments() {
        Comment[] userList =
                restTemplate.getForObject("https://gorest.co.in/public/v2/comments", Comment[].class);
        return Arrays.asList(userList);
    }
}
