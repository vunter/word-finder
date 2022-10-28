package com.example.demo;

import com.example.demo.model.FiltersModel;
import com.example.demo.services.CacheService;
import com.example.demo.services.FinderService;
import com.example.demo.services.impl.LocalCacheImpl;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }


  @SneakyThrows
  @Bean
  ApplicationRunner runner(@Autowired CacheService<String> cacheService, @Autowired FinderService finderService) {
    return args -> {
      try (var br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:br-files/br-sem-acentos.txt")))) {
        var line = br.readLine();
        while (Strings.isNotBlank((line = br.readLine()))) {
          cacheService.add(line.toLowerCase(Locale.ROOT));
        }
      }
      finderService.findWordsWithFilterObject(FiltersModel.builder()
        .withLetters("iu")
        .endLetters("o")
        .withoutLetters("fracens")
//        .startLetters("ra")
//          .withoutThisSequence("")
//          .withThisSequence("")
          .maxNumLetters(5L)
          .minNumLetters(5L)
        .build()).forEach(System.out::println);
    };
  }

  @Bean
  public CacheService<String> cacheService() {
    return new LocalCacheImpl<>();
  }
}
