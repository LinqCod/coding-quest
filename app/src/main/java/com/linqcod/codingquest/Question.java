package com.linqcod.codingquest;

public class Question {

    private String question;
    private int imageUri;
    private boolean hasImage;
    private String[] answers;
    private String rightAnswer;

    public Question(String question, int imageUri, boolean hasImage, String[] answers, String rightAnswer) {
        this.question = question;
        this.imageUri = imageUri;
        this.hasImage = hasImage;
        this.answers = answers.clone();
        this.rightAnswer = rightAnswer;
    }

    public boolean answer(String answer) {
        return answer.equals(rightAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public int getImageUri() {
        return imageUri;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public String[] getAnswers() {
        return answers.clone();
    }
}
