package com.lms.teste.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.teste.Models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}
