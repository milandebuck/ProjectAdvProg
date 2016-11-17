/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * A single exercise with room for an answer
 * @author Dieter
 */
public class Exercise {
    private String word;
    private String nativeLanguage;
    private String translationLanguage;
    private String answer;

    public Exercise(String word, String nativeLanguage, String translationLanguage) {
        this.word = word;
        this.nativeLanguage = nativeLanguage;
        this.translationLanguage = translationLanguage;
    }

    public Exercise(String word, String nativeLanguage, String translationLanguage, String answer) {
        this.word = word;
        this.nativeLanguage = nativeLanguage;
        this.translationLanguage = translationLanguage;
        this.answer = answer;
    }

    public String getWord() {
        return word;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public String getTranslationLanguage() {
        return translationLanguage;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
}
