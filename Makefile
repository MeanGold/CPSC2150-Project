SOURCE_FILES := cpsc2150/extendedConnects/GameScreen.java \
	cpsc2150/extendedConnectX/models/AbsGameBoard.java \
	cpsc2150/extendedConnectX/models/BoardPosition.java \
	cpsc2150/extendedConnectX/models/IGameBoard.java \
	cpsc2150/extendedConnectX/models/GameBoard.java \
	cpsc2150/extendedConnectX/models/GameBoardMem.java \
	cpsc2150/extendedConnectX/tests/TestGameBoard.java \
	cpsc2150/extendedConnectX/tests/TestGameBoardMem.java

CLASSES := $(patsubst %.java, %.class, $(SOURCE_FILES))

default: $(CLASSES) build/Manifest.mf
	@mkdir -p build
	jar cfm build/Project2.jar build/Manifest.mf $(CLASSES)

run: default
	java -jar build/Project2.jar

clean:
	# remove temporary directory and recursively remove .class files
	rm -rf build/
	find . -type f -name '*.class' -delete

%.class: %.java
	javac $<

build/Manifest.mf:
	# make our manifest file, it sets up `GameScreen.main` as the entry point
	@mkdir -p build
	echo "Main-Class: cpsc2150.extendedConnects.GameScreen" > build/Manifest.mf

test: $(SOURCE_FILES)
	javac -cp .:/usr/share/java/junit4.jar $(SOURCE_FILES)

testGB: $(CLASSES)
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoard

testGBMem: $(CLASSES)
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoardMem
