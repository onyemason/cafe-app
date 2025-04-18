package com.inn.cafe.dao;

import com.inn.cafe.POJO.User;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
//  List<UserWrapper>getAllUser();
//  List<String> getAlLAdmin();
//  Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

  @Query(value = "SELECT * FROM users u", nativeQuery = true)
  List<User> getAllUsers();

  @Query(value = "SELECT * FROM users u where u.role=:role", nativeQuery = true)
  List<User> getAllAdminUsers(@Param("role") String role);

  @Query(value = "SELECT  * FROM users u where u.email=:email OR u.contact_number=:contact_number", nativeQuery = true)
  User findByEmailOrPassword(@Param("email") String email, @Param("contact_number") String contact_number);

  @Modifying
  @Transactional
  @Query(value = "UPDATE users SET password =:password WHERE email=:email", nativeQuery = true)
  void updatePassword(@Param("password") String password, @Param("email") String email);

  Boolean existsByEmail(String email);
  Boolean existsByContactNumber(String contactNumber);




}
