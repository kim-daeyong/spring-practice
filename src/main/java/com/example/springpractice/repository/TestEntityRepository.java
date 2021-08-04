package com.example.springpractice.repository;

import com.example.springpractice.entity.TestEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long>{
    
}