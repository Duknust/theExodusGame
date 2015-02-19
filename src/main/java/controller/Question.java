package controller;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class Question {
    private String question=null;
    private String answer1=null;
    private String answer2=null;
    private String answer3=null;
    private String answer4=null;
    private String correctAnswer=null;

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {return question;}
    public String getAnswer1() {return answer1;}
    public String getAnswer2() {return answer2;}
    public String getAnswer3() {return answer3;}
    public String getAnswer4() {return answer4;}
    public String getcorrectAnswer() {return correctAnswer;}

    public void setQuestion(String question) {this.question = question;}
    public void setAnswer1(String answer) {this.answer1 = answer;}
    public void setAnswer2(String answer) {this.answer2 = answer;}
    public void setAnswer3(String answer) {this.answer3 = answer;}
    public void setAnswer4(String answer) {this.answer4 = answer;}
    public void setCorrectAnswer(String answer) {this.correctAnswer = answer;}

    @Override
    public String toString() {
        return "Question: " + question;
    }
}

