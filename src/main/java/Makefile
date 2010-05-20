JAVAC = javac
JAVAC_FLAGS = -sourcepath .

JAVA = java
JAVA_FLAGS =

CUP = -cp ../../../lib/java_cup-0.11a.jar java_cup.Main
CUPFLAGS = -nopositions -expect 100

JLEX = -cp ../../../lib/jlex-1.2.6.jar JLex.Main

LATEX = latex
DVIPS = dvips

all: pgf/Trees/Yylex.java pgf/Trees/sym.java pgf/Trees/parser.java

pgf/Trees/Yylex.java: pgf/Trees/Yylex
	${JAVA} ${JAVA_FLAGS} ${JLEX} pgf/Trees/Yylex

pgf/Trees/sym.java pgf/Trees/parser.java: pgf/Trees/Trees.cup
	${JAVA} ${JAVA_FLAGS} ${CUP} ${CUPFLAGS} pgf/Trees/Trees.cup
	mv sym.java parser.java pgf/Trees/

clean:
	 rm -f pgf/Trees/Absyn/*.class pgf/Trees/*.class
	 rm -f .dvi Trees.aux Trees.log Trees.ps  *.class

bnfc:
	bnfc -java1.5 -p pgf Trees.cf

