package com.tw.apistackbase.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
