
JAVA_CLASS_PATH="../target/scala_2.8.0/classes/:../project/boot/scala-2.8.0/lib/scala-library.jar"


Eval.class: Eval.java
	javac -cp ${JAVA_CLASS_PATH} $<

run-java: Eval.class
	time java -cp ${JAVA_CLASS_PATH}:. Eval Phrasebook.pgf:PhrasebookEng < phrases.txt 

clean:
	rm -rf Eval.class
