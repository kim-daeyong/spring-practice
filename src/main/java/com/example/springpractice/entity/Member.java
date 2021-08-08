package com.example.springpractice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
public class Member {

    @Id 
    @GeneratedValue(strategy=  GenerationType.SEQUENCE)
    private long MemberId;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Account> accounts;

}