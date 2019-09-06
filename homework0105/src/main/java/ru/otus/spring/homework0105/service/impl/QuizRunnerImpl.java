package ru.otus.spring.homework0105.service.impl;

import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.QuizRunner;
import ru.otus.spring.homework0105.service.QuizService;
import ru.otus.spring.homework0105.util.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.out;

public class QuizRunnerImpl implements QuizRunner {
    private QuizService quizService;

    public QuizRunnerImpl(QuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public void startQuiz(List<QuizUnit> quizUnitList) {
        try (Scanner scanner = new Scanner(System.in)) {
            int userAnswer, score = 0;
            String userName, userSurname;
            List<QuizAnswer> quizAnswerList = new ArrayList<>();

            out.printf("%S\n%s\n", "Welcome to the Quiz!", "Please, enter your name.");
            userName = scanner.nextLine().trim();

            out.println("Please, enter your surname.");
            userSurname = scanner.nextLine().trim();

            User user = new User(userName, userSurname);

            out.printf("%S%s %s\n%s%d%s\n%s\n", "Welcome ", user.getName(), user.getSurname(),
                    "You will be asked ", quizUnitList.size(), " questions, ready? Go!",
                    "For every questions choose the right answer and enter the number.");

            for (QuizUnit quizUnit : quizUnitList) {
                int rightAnswer = quizUnit.getRightAnswer();
                List<String> answers = quizUnit.getAnswers();

                // Print quiz unit
                out.printf("\t%s\n", quizUnit.getQuestion());
                for (int i = 0; i < answers.size(); i++) {
                    out.printf("\t%d: %s\n", i + 1, answers.get(i));
                }

                // Get user answer
                while (true) {
                    String nextLine = scanner.nextLine().trim();
                    if (nextLine.matches("[1-4]")) {
                        userAnswer = Integer.parseInt(nextLine);
                        break;
                    } else {
                        out.println("Please, enter the correct number (from 1 to 4)");
                    }
                }
                quizAnswerList.add(new QuizAnswer(quizUnit.getQuizId(), userAnswer));

                out.println("Your answer is " + answers.get(userAnswer - 1));
                if (userAnswer == rightAnswer) {
                    score++;
                    out.println("Yes, you are right! +1 point");
                } else {
                    out.println("No, sorry, the right answer is " + answers.get(rightAnswer - 1));
                }
                out.println();
            }

            //Save the result
            QuizResult quizResult = new QuizResult(UUID.randomUUID().toString(), user, quizAnswerList, Result.getResult(score));
            quizService.saveQuizResult(quizResult);
        }
    }
}
