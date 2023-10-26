package com.generation.javago.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.javago.model.entity.Seasons;

public interface SeasonsRepository extends JpaRepository<Seasons, Integer>
{

}
