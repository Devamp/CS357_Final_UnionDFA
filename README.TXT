# DFA Union Program

## Program Overview

This program, developed by Devam Patel and Harrison Winters as part of the Final Project for CS357, aims to demonstrate that regular languages are closed under union. Given two Deterministic Finite Automata (DFAs) defined in .txt files, the program produces the union of the two DFAs. The resulting DFA represents the union of the languages recognized by the original DFAs.

## Expected Inputs

The program expects two .txt files, each containing the formal definitions ({Q}, Σ, δ, q0, {F}) for two DFAs. These input files should provide the necessary information to define the DFAs. Examples of input files can be found in the provided sample input files.

Please note that the input format used in this program is .txt files. This approach was chosen due to issues with downloading the proper parser for JSON files.

## Expected Output

The program outputs the formal definition of the "Unioned" DFA in the `unionDFA.txt` file. The resulting DFA represents the union of the languages recognized by the input DFAs. Example output files can be found in the "Test Cases" folder, where each file name, such as `testCase_inp1_inp2.txt`, indicates the union DFA of the DFA defined in `input1.txt` and the DFA defined in `input2.txt`.

## How to Run the Program

To compile and run the program, follow these steps:

1. Compile the program using the following command:
```
javac UnionDFA.java
```
2. Run the program
```
java UnionDFA
```
3. Follow the prompts to enter the names of the two .txt files containing the formal definitions of the DFAs you want to union.
4. The result will be generated and saved in the unionDFA.txt file.

Make sure to have the necessary .txt input files in the same directory as the program, and ensure that you have Java installed to run the program.
