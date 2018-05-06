all: Main.java
	javac Main.java
	java Main
	display image.png
	rm image.ppm
run: all
clean:
	rm *.class
