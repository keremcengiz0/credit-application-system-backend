package com.keremcengiz0.creditapplicationsystem.services.concretes;

import com.keremcengiz0.creditapplicationsystem.dtos.CustomerDTO;
import com.keremcengiz0.creditapplicationsystem.utils.ScoreServiceTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Test
    void generateRandomScore() {
        int score = scoreService.generateRandomScore("12345678912");
        assertTrue(score > 300 && score <2000);
    }

    @Test
    void getScore() {
        scoreService.generateRandomScore("12345678901");
        int score = scoreService.getScore("12345678912");
        assertTrue(score >= 300 && score <= 1999);
    }
}