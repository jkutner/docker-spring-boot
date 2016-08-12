package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@SpringBootApplication
@RequestMapping("/")
public class DemoApplication {

  private RecordRepository repository;

  @Autowired
  public DemoApplication(RecordRepository repository) {
    this.repository = repository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String home(ModelMap model) {
    List<Record> records = repository.findAll();
    model.addAttribute("records", records);
    model.addAttribute("insertRecord", new Record());
    return "home";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String insertData(ModelMap model,
                           @ModelAttribute("insertRecord") @Valid Record record,
                           BindingResult result) {
    if (!result.hasErrors()) {
      repository.save(record);
    }
    return home(model);
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
