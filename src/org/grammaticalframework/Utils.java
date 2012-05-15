package org.grammaticalframework;


public class Utils {

    public static String concat(String[] tokens) {
	return concat(tokens, " ");
    }

    public static String concat(String[] tokens, String sep) {
	if (tokens.length < 1)
	    return "";
	StringBuffer sb = new StringBuffer(tokens[0]);
	for (int i = 1; i < tokens.length; i++) {
	    sb.append(" ");
	    sb.append(tokens[i]);
	}
	return sb.toString();
    }


}