JAVAC = javac
JAVAC_FLAGS = -sourcepath .

JAVA = java
JAVA_FLAGS =

CUP = java_cup.Main
CUPFLAGS = -nopositions -expect 100

JLEX = JLex.Main

LATEX = latex
DVIPS = dvips

all: test Trees.ps

test: absyn Trees/Yylex.class Trees/PrettyPrinter.class Trees/Test.class Trees/ComposVisitor.class Trees/AbstractVisitor.class Trees/FoldVisitor.class Trees/AllVisitor.class Trees/parser.class Trees/sym.class Trees/Test.class

.PHONY: absyn

%.class: %.java
	${JAVAC} ${JAVAC_FLAGS} $^

absyn: Trees/Absyn/Tree.java Trees/Absyn/Lambda.java Trees/Absyn/Variable.java Trees/Absyn/Application.java Trees/Absyn/Literal.java Trees/Absyn/MetaVariable.java Trees/Absyn/Function.java Trees/Absyn/Lit.java Trees/Absyn/IntLiteral.java Trees/Absyn/FloatLiteral.java Trees/Absyn/StringLiteral.java
	${JAVAC} ${JAVAC_FLAGS} $^

Trees/Yylex.java: Trees/Yylex
	${JAVA} ${JAVA_FLAGS} ${JLEX} Trees/Yylex

Trees/sym.java Trees/parser.java: Trees/Trees.cup
	${JAVA} ${JAVA_FLAGS} ${CUP} ${CUPFLAGS} Trees/Trees.cup
	mv sym.java parser.java Trees/

Trees/Yylex.class: Trees/Yylex.java Trees/sym.java

Trees/sym.class: Trees/sym.java

Trees/parser.class: Trees/parser.java Trees/sym.java

Trees/PrettyPrinter.class: Trees/PrettyPrinter.java

Trees.dvi: Trees.tex
	${LATEX} Trees.tex

Trees.ps: Trees.dvi
	${DVIPS} Trees.dvi -o Trees.ps

clean:
	 rm -f Trees/Absyn/*.class Trees/*.class
	 rm -f .dvi Trees.aux Trees.log Trees.ps  *.class

vclean:
	 rm -f Trees/Absyn/Tree.java Trees/Absyn/Lambda.java Trees/Absyn/Variable.java Trees/Absyn/Application.java Trees/Absyn/Literal.java Trees/Absyn/MetaVariable.java Trees/Absyn/Function.java Trees/Absyn/Lit.java Trees/Absyn/IntLiteral.java Trees/Absyn/FloatLiteral.java Trees/Absyn/StringLiteral.java Trees/Absyn/Tree.class Trees/Absyn/Lambda.class Trees/Absyn/Variable.class Trees/Absyn/Application.class Trees/Absyn/Literal.class Trees/Absyn/MetaVariable.class Trees/Absyn/Function.class Trees/Absyn/Lit.class Trees/Absyn/IntLiteral.class Trees/Absyn/FloatLiteral.class Trees/Absyn/StringLiteral.class
	 rm -f Trees/Absyn/*.class
	 rmdir Trees/Absyn/
	 rm -f Trees.tex Trees.dvi Trees.aux Trees.log Trees.ps 
	 rm -f Trees/Yylex Trees/Trees.cup Trees/Yylex.java Trees/VisitSkel.java Trees/ComposVisitor.java Trees/AbstractVisitor.java Trees/FoldVisitor.java Trees/AllVisitor.java Trees/PrettyPrinter.java Trees/Skeleton.java Trees/Test.java Trees/sym.java Trees/parser.java Trees/*.class
	 rm -f Makefile
	 rmdir -p Trees/

