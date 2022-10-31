package com.codestates.preproject.controller;

import com.codestates.preproject.dto.UserAndToken;
import com.codestates.preproject.dto.UserDTO;
import com.codestates.preproject.service.UserService;
import com.codestates.preproject.util.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value="/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping(value="/sign")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "user submitted")
    public void userSignUp(@RequestBody UserDTO.User user){
        userService.saveUserData(user);
    }

    @CrossOrigin
    @PostMapping(value="/login") //status로 처리하면 좋다.
    public CommonResponse<UserAndToken> login(@RequestBody UserDTO.User user){
        UserAndToken userAndToken = userService.login(user);
        if(userAndToken.getEmail().equals("user")){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"No User");
        }else if(userAndToken.getEmail().equals("password")){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"InVaild password");
        }
        return new CommonResponse<>(userAndToken, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value="/logout")
    public CommonResponse<Boolean> logout(@RequestBody UserDTO.User user){
        return new CommonResponse<Boolean>(userService.logout(user),HttpStatus.OK);
    }
}
