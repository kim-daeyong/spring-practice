package com.example.springpractice.repository;

import com.example.springpractice.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
}