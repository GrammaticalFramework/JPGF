package org.grammaticalframework;

public class UnknownLanguageException extends Exception {
    private String language;
    public UnknownLanguageException(String language) {
	this.language = language;
    }
    
    public String getLanguage() {
	return this.language;
    }
    
    public String toString() {
	return "Unknown language: " + language;
    }
}
