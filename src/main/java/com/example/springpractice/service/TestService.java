package com.example.springpractice.service;

import java.util.NoSuchElementException;


import com.example.springpractice.entity.TestEntity;
import com.example.springpractice.entity.dto.TestEntityDto;
import com.example.springpractice.repository.TestEntityRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    
    private final TestEntityRepository testEntityRepository;

    public TestService(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    public long addTestEntoty(long order) {
        TestEntity testEntity = TestEntity.builder().count(order).build();

        testEntityRepository.save(testEntity);

        return testEntity.getId();
    }

    @Transactional
    public String modifyTestEntity(long id) {
        TestEntity testEntity = testEntityRepository.findById(id)
                                                    .orElseThrow(
                                                        () -> new NoSuchElementException("Not Found :" + id));

        testEntity.setCount(testEntity.getCount() - 1);

        // testEntityRepository.save(testEntity);

        return "success";
    }

    public TestEntityDto getTestEntity(long id) {
        TestEntity testEntity = testEntityRepository.findById(id)
                                                    .orElseThrow(
                                                        () -> new NoSuchElementException("Not Found :" + id));

        return TestEntityDto.builder()
                            .id(testEntity.getId())
                            .count(testEntity.getCount())
                            .build();
    }

    @Transactional
    public TestEntityDto getTestEntityTransaction(long id) {
        TestEntity testEntity = testEntityRepository.findById(id)
                                                    .orElseThrow(
                                                        () -> new NoSuchElementException("Not Found :" + id));

        return TestEntityDto.builder()
                            .id(testEntity.getId())
                            .count(testEntity.getCount())
                            .build();
    }

    @Transactional(readOnly = true)
    public TestEntityDto getTestEntityTransactionReadOnly(long id) {
        TestEntity testEntity = testEntityRepository.findById(id)
                                                    .orElseThrow(
                                                        () -> new NoSuchElementException("Not Found :" + id));

        return TestEntityDto.builder()
                            .id(testEntity.getId())
                            .count(testEntity.getCount())
                            .build();
    }
}