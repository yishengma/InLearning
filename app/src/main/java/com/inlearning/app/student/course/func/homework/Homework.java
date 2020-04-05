package com.inlearning.app.student.course.func.homework;

import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.Question;

public class Homework {
    private Question mQuestion;
    private Answer mAnswer;


    public Question getQuestion() {
        return mQuestion;
    }

    public Homework setQuestion(Question question) {
        mQuestion = question;
        return this;
    }

    public Answer getAnswer() {
        return mAnswer;
    }

    public Homework setAnswer(Answer answer) {
        mAnswer = answer;
        return this;
    }
}
