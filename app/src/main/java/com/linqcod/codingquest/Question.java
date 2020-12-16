package com.linqcod.codingquest;

public class Question {

    private String question;
    private int imageUri;
    private String[] answers;
    private String rightAnswer;

    public Question(String question, int imageUri, String[] answers, String rightAnswer) {
        this.question = question;
        this.imageUri = imageUri;
        this.answers = answers.clone();
        this.rightAnswer = rightAnswer;
    }

    public boolean answer(int ansIndex) {
        return answers[ansIndex].equals(rightAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public int getImageUri() {
        return imageUri;
    }

    public String[] getAnswers() {
        return answers.clone();
    }
}
