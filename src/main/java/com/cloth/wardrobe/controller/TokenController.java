//package com.cloth.wardrobe.controller;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Collections;
//
//@RequiredArgsConstructor
//@Controller
//public class TokenController {
//
//    @GetMapping
//    private ResponseEntity<?> issueToken() {
//
//    }
//
//    @PostMapping
//    private ResponseEntity<?> verifyGoogleToken() {
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                .setAudience(Collections.singletonList(CLIENT_ID))
//    }
//}
