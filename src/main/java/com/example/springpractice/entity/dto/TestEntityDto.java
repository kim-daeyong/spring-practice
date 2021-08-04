package com.example.springpractice.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestEntityDto {

    private long id;
    
    private long count;
    
}