package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

  @GetMapping("/hi")  // URL 요청 연결
  public String niceToMeetYou(Model model) {
    model.addAttribute("username", "Jiwoo");
    return "greetings"; // templates/greetings.mustaache -> 브라우저로 전송
  }

  @GetMapping("/bye")
  public String seeYouNext(Model model) {
    model.addAttribute("nickname", "홍길동");
    return "goodbye";
  }
}
