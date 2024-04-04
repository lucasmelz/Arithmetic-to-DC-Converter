# Arithmetic to DC Converter

## Introduction

This project is an integer calculator that parses arithmetic expressions with variables, converts them into Reverse Polish Notation (RPN), and generates instructions compatible with the `dc` calculator program. The `dc` program is a reverse-Polish desk calculator which supports arbitrary-precision arithmetic.

## Prerequisites

- Java Development Kit (JDK) 8 or newer
- ANTLR 4.13.1 runtime library
- ANTLR 4.13.1 tool for generating parser and lexer (optional for already generated sources)

## Setup

1. **Install JDK** (if not installed): Ensure Java Development Kit (JDK) is installed on your system. You can verify this by running `java -version` in your terminal.
2. **ANTLR Runtime**: Download the ANTLR 4.13.1 runtime jar (`antlr-runtime-4.13.1.jar`) from the [ANTLR website](https://www.antlr.org/download/antlr-4.13.1-complete.jar) and place it in your project directory.
3. **Generate Parser and Lexer** (if necessary): Run the following command in your project directory where your `.g4` grammar file is located:
   ```sh
   antlr4 -visitor Arithmetic.g4
   ```
   This step is only needed if you modify the grammar and need to regenerate the parser/lexer files.

## Running the Program

To run the program, compile the Java sources including the ANTLR runtime in the classpath, and then execute the `Main` class. Here are the steps:

1. **Compile the Program**: Navigate to your project directory and compile the Java files using the following command:

   ```sh
   javac -cp .:antlr-runtime-4.13.1.jar *.java
   ```

   On Windows, use `;` instead of `:` in the classpath.

2. **Run the Program**: Use the following command to run the program:
   ```sh
   java -cp .:antlr-runtime-4.13.1.jar Main
   ```
   You can then input your arithmetic expressions. To end the input and process the expressions, send an EOF signal (`Ctrl+D` on Unix/Linux/macOS, `Ctrl+Z` on Windows) after your last expression.

### Example Usage

- **Direct Input**:
  ```sh
  echo 'a=3*4 ; x=2+a*5' | java -cp .:antlr-runtime-4.13.1.jar Main | dc
  ```
- **Using a File**:
  If you have a file `input.txt` with arithmetic expressions, you can process it as follows:
  ```sh
  cat input.txt | java -cp .:antlr-runtime-4.13.1.jar Main | dc
  ```
