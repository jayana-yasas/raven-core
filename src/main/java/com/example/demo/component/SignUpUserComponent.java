package com.example.demo.component;

import com.example.demo.component.steps.CheckUserExists;
import com.example.demo.component.steps.SaveUser;
import com.example.demo.dto.request.SignUpDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SignUpUserComponent {

    private final CheckUserExists checkUserExists;
    private final SaveUser saveUser;

    public Boolean signUp(String traceId, SignUpDto signUpDto) {
        boolean userExists = checkUserExists.byEmail(traceId, signUpDto.getEmail());
        if(userExists){
            throw new UserException("User is already registered");
        }

        User user = mapTo(signUpDto);

        saveUser.save(traceId, user);
        return true;
    }

    private User mapTo(SignUpDto signUpDto){
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(signUpDto.getPassword());
        user.setPhoneNumber(signUpDto.getPhoneNumber());
        user.setFirstName(signUpDto.getName());
        return user;
    }

}
