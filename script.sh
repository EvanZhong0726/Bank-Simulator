#!/bin/bash

javac -d out ./src/bank/*.java
java -cp out bank.Bank
