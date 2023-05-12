package com.snopko.rest.logic.service;

import com.snopko.rest.dao.repository.IUserProfileRepository;
import com.snopko.rest.logic.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private IUserProfileRepository repository;

    public void add(UserDto dto){

    }
}
