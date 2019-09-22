package ru.otus.spring.homework0105;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.homework0105.service.QuizRunner;

@Configuration
@SpringBootApplication
public class Application {

    public Application(ApplicationContext applicationContext) {
        QuizRunner quizRunner = applicationContext.getBean(QuizRunner.class);
        quizRunner.startQuiz();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
