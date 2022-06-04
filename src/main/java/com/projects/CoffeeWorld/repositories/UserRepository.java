package com.projects.CoffeeWorld.repositories;
import com.projects.CoffeeWorld.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findUserByEmail(String email);
    User findUserByActivationCode(String code);
}
