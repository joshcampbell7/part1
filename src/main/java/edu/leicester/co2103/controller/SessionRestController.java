package edu.leicester.co2103.controller;

import edu.leicester.co2103.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionRestController {
    @Autowired
    SessionRepository sessionRepository;




}
