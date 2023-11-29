package com.example.cs4084_project;

//An Exercise class for use in the Exercise planner
public class Exercise {
    public String name; //Name of the Exercise
    public char difficulty, bodyPart; //Difficulty will only be E, M or H (Easy,Medium,Hard), Bodypart will only be A, C or L (Arms,Core,Legs)
    private int sets, reps, weight; //The Exercise info
    private int restMins = 2; //How long user should rest between each set of an Exercise

    //Constructors
    public Exercise(String name, char difficulty, char bodyPart) {
        this.name = name;
        this.difficulty = difficulty;
        this.bodyPart = bodyPart;
        weight = 10;
        difficultyFormula();
    }

    public Exercise(String name, char difficulty, char bodyPart, int baseWeight) {
        this.name = name;
        this.difficulty = difficulty;
        this.bodyPart = bodyPart;
        weight = baseWeight;
        difficultyFormula();
    }

    //Method that determines the amount of sets and reps in a single exercise, as well as the rest time
    private void difficultyFormula() {
        switch (difficulty) {
            case 'E':
                sets = 5;
                reps = 8;
                break;
            case 'M':
                sets = 6;
                reps = 10;
                restMins += 2;
                weight += 7.5;
                break;
            case 'H':
                sets = 10;
                reps = 10;
                restMins += 3;
                weight += 15;
                break;
        }
    }

    //A toString method returning the Exercise info in a single String
    @Override
    public String toString() {
        return "Exercise Name: " + name +
                "\nWeight: " + weight + "KG plus bar" +
                "\nSets:" + sets +
                "\nReps Per Set:" + reps +
                "\nRest time between each set: " + restMins;
    }
}
