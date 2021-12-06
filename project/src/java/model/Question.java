package model;

import java.util.Arrays;

public class Question {

    private String text;
    private String[] options;
    private String[] answer;
    
    
    public Question() {
        
    }
    

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the options
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(String options) {
        this.options = options.split("/");
    }

    /**
     * @return the answer
     */
    public String[] getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer.split("/");
    }
}
