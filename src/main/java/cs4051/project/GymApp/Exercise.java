package cs4051.project.GymApp;

public class Exercise {
    public String name;
    public char difficulty, bodyPart; //Difficulty will only be E, M or H (Easy,Medium,Hard), Bodypart will only be A, C or L (Arms,Core,Legs)
    private int sets, reps, weight;
    private int restMins = 2;


    public Exercise(String name, char difficulty, char bodyPart){
        this.name = name;
        this.difficulty = difficulty;
        this.bodyPart = bodyPart;
        weight = 10;
        difficultyFormula();
    }

    public Exercise(String name, char difficulty, char bodyPart, int baseWeight){
        this.name = name;
        this.difficulty = difficulty;
        this.bodyPart = bodyPart;
        weight = baseWeight;
        difficultyFormula();
    }

    private void difficultyFormula(){
        switch (difficulty) {
            case 'E':
                sets = 5; reps = 8;
                break;
            case 'M':
                sets = 6; reps = 10;
                restMins += 2; weight += 7.5;
                break;
            case 'H':
                sets = 10; reps = 10;
                restMins += 3; weight += 15;
                break;
        }
    }

    @Override
    public String toString(){
        return "Exercise Name: " + name + "\nWeight: " + weight + "KG plus bar\nSets:" + sets + "\nReps Per Set:" + reps + "\nRest time between each set: " + restMins;
    }
}