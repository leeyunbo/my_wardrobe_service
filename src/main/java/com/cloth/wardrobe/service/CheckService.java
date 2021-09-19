package com.cloth.wardrobe.service;


import com.cloth.wardrobe.exception.WrongAccessException;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    protected void confirmRightApproach(String emailForSession, String email) {
        if (!emailForSession.equals(email)) {
            throw new WrongAccessException("올바르지 않은 접근입니다.");
        }
    }
}
