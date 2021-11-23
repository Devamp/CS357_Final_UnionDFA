Program Overview:
    This program is developed by Devam Patel and Harrison Winters as part of the Final Project for CS357. Given two DFAs, this program produces the union of the two DFA; showing that regular languages are closed under union.

Expected Inputs:
	Program expects two .txt files containing the formal definations ({Q}, Σ, δ, q0, {F}) for two DFAs. Examples of inputs can be seen in the 5 input files provided. **This is different from the .JSON input defined in the proposal as we were having trouble downloading the proper parser for JSON file. So we decided to work with .txt files**

Expected Output:
	Program outputs the formal defination for the "Unioned" DFA in the unionDFA.txt file. 

How to run the program:
	1. Program can be compiled by the following command:
		javac main.java
	2. Program can be ran by the following command:
		java main
	3. Enter the two names of the .txt DFA files as prompted.
    4. Result will be returned in unionDFA.txt