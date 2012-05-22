package org.grammaticalframework;

import org.grammaticalframework.reader.*;
import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.Trees.Absyn.Tree;


public class Parser {
    private Concrete language;
    private String startcat;
    /* ******************************** API ******************************** */
    public Parser(PGF pgf, Concrete language) {
        this.language = language;
        this.startcat = pgf.getAbstract().startcat();
    }

    public Parser(PGF pgf, String language)
        throws UnknownLanguageException
    {
        this(pgf, pgf.concrete(language));
    }

    
    public void setStartcat(String startcat) {
        this.startcat = startcat;
    }


    /**
     * Parse the given list of tokens
     * @param tokens the input tokens
     * @return the corresponding parse-state
     **/
    public ParseState parse(String[] tokens) {
        ParseState ps = new ParseState(this.language, this.startcat);
        for (String w : tokens)
            if (!ps.scan(w))
                break;
        return ps;
    }

    /**
     * Parse the given list of tokens
     * @param tokens the input tokens
     * @return an array of trees
     **/
    // FIXME: not using the start category ??
    public Tree[] parseToTrees(String[] tokens) {
        return this.parse(tokens).getTrees();
    }

    /**
     * Parse the given string
     * uses a very basic tokenizer that split on whitespaces.
     * @param phrase the input string
     * @return the corresponding parse-state
     **/
    public ParseState parse(String phrase) {
        return this.parse(phrase.split(" "));
    }

    /**
     * Parses the empty string
     * (usefull for completion)
     * @param startcat the start category
     * @return the corresponding parse-state
     **/
    public ParseState parse() {
        return this.parse(new String[0]);
    }

}