package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

  @Autowired // 스프링 부트가 미리 생성해놓은 리파지터리 객체를 가져옴(DI)
  private ArticleRepository articleRepository;

  @GetMapping("/articles/new")
  public String newArticleForm() {
    return "articles/new";
  }

  @PostMapping("/articles/create")
  public String createArticle(ArticleForm form) {
    log.info(form.toString());    // println() 을 로깅으로 대체!

    // 1. Dto를 Entity 변환
    Article article = form.toEntity();
    log.info(article.toString()); // println() 을 로깅으로 대체!

    // 2. Repository에게 Entity를 DB로 저장하게 함
    Article saved = articleRepository.save(article);
    log.info(article.toString()); // println() 을 로깅으로 대체!

    // 리다이렉트 적용: 생성 후, 브라우저가 해당 URL로 재요청
    return "redirect:/articles/" + saved.getId();
  }

  @GetMapping("/articles/{id}") // 해당 URL요청을 처리 선언
  public String show(@PathVariable Long id, Model model) { // URL에서 id를 변수로 가져옴
    log.info("id = " + id);
    // 1: id로 데이터를 가져옴!
    Article articleEntity = articleRepository.findById(id).orElse(null);

    // 2: 가져온 데이터를 모델에 등록!
    model.addAttribute("article", articleEntity);

    // 3: 보여줄 페이지를 설정!
    return "articles/show";

  }

  @GetMapping("/articles/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {
    // 수정할 데이터 가져오기
    Article articleEntity = articleRepository.findById(id).orElse(null);

    // 모델에 데이터 등록
    model.addAttribute("article", articleEntity);

    // 뷰 페이지 설정
    return "articles/edit";
  }

  @GetMapping("/articles")
  public String index(Model model) {
    // 1: 모든 Article을 가져온다!
    List<Article> articleEntityList = articleRepository.findAll();

    // 2: 가져온 Article 묶음을 뷰로 전달!
    model.addAttribute("articleList", articleEntityList);

    // 3: 뷰 페이지를 설정!
    return "articles/index";
  }
}