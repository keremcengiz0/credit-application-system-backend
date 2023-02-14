package com.keremcengiz0.creditapplicationsystem.services.concretes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Random;

@Slf4j
@Service
public class ScoreService {
    private int score;
    private boolean success;

    public int generateRandomScore(String identityNumber) {
        Random random = new Random();
        this.score = random.nextInt(1700) + 300;
        log.info("Random score generated.");
        return score;
    }

    public void setSuccess() {
        this.success = this.score >= 500;
    }

    public int getScore(String identity) {
        return this.score;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
