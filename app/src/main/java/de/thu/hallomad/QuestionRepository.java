package de.thu.hallomad;

import java.util.ArrayList;
import java.util.Random;

public class QuestionRepository {
    private static ArrayList<Question> questionBank;
    private Random random;

    public QuestionRepository() {
        questionBank = QuestionBank();
        random = new Random();
    }


    private static ArrayList<Question> QuestionBank() {
        ArrayList<Question> questionBank = new ArrayList<>();
        // question 1
        String[] answers = {"Hand-and-foot", "Foot-in-mouth", "Hand-to-mouth", "Foot-and-mouth"};
        Question question = new Question("Which disease devastated livestock across the UK during 2001?", answers, 1);
        questionBank.add(question);
        // question 2
        answers = new String[]{"Transaction", "Total", "Tax", "Trauma"};
        question = new Question("In the UK, VAT stands for value-added ...?", answers, 2);
        questionBank.add(question);
        //question 3
        answers = new String[]{"Throw it", "Punch it", "Kick it", "Eat it"};
        question = new Question("What are you said to do to a habit when you break it?", answers, 2);
        questionBank.add(question);
        //question 4
        answers = new String[]{"Tables", "Gables", "Stables", "Cables"};
        question = new Question("What might an electrician lay?", answers, 3);
        questionBank.add(question);

        //question 5
        answers = new String[]{"Elbow room", "Foot rest", "Ear hole", "Knee lounge"};
        question = new Question("Which of these means adequate space for moving in?", answers, 0);
        return questionBank;
    }

    Question randomQuestion() {
        int index = random.nextInt(questionBank.size());
        return questionBank.get(index);
    }
}
