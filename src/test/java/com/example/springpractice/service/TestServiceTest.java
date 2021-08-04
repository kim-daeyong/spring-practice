package com.example.springpractice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springpractice.entity.dto.TestEntityDto;


@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TestService testService;

    // public final TestService testService;

    // public TestServiceTest(TestService testService) {
    //     this.testService = testService;
    // } // junit5 에선 생성자 주입 X, 필드주입 O JUnit5가 DI를 스스로 지원한다고 함

    @Test
    public void addTest() {
        testService.addTestEntoty(100001l);

        TestEntityDto dto = testService.getTestEntity(1);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);

    }

    @Test
    public void modifyTest() {
        testService.modifyTestEntity(1);

        TestEntityDto dto = testService.getTestEntity(1);

        assertThat(dto).isNotNull();
    }
    
}