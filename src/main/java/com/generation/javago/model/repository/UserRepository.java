package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>
{

}
