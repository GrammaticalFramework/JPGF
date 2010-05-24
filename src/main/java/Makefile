JAVAC = javac
JAVAC_FLAGS = -sourcepath .

JAVA = java
JAVA_FLAGS =

CUP = -cp ../../../lib/java_cup-0.11a.jar java_cup.Main
CUPFLAGS = -nopositions -expect 100

JLEX = -cp ../../../lib/jlex-1.2.6.jar JLex.Main

LATEX = latex
DVIPS = dvips

all: org/grammaticalframework/Trees/Yylex.java org/grammaticalframework/Trees/sym.java org/grammaticalframework/Trees/parser.java

org/grammaticalframework/Trees/Yylex.java: org/grammaticalframework/Trees/Yylex
	${JAVA} ${JAVA_FLAGS} ${JLEX} org/grammaticalframework/Trees/Yylex

org/grammaticalframework/Trees/sym.java org/grammaticalframework/Trees/parser.java: org/grammaticalframework/Trees/Trees.cup
	${JAVA} ${JAVA_FLAGS} ${CUP} ${CUPFLAGS} org/grammaticalframework/Trees/Trees.cup
	mv sym.java parser.java org/grammaticalframework/Trees/

clean:
	 rm -f org/grammaticalframework/Trees/Absyn/*.class org/grammaticalframework/Trees/*.class
	 rm -f .dvi Trees.aux Trees.log Trees.ps  *.class

bnfc:
	bnfc -java1.5 -p org.grammaticalframework Trees.cf

