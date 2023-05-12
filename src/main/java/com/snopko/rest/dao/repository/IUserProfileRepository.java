package com.snopko.rest.dao.repository;

import com.snopko.rest.dao.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface IUserProfileRepository extends CrudRepository<UserProfile, Long> {
}
