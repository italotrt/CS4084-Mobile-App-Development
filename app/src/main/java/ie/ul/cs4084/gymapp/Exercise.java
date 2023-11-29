package ie.ul.cs4084.gymapp;

// An Exercise class for use in the Exercise planner
public class Exercise {
    public String name, bodyPart, reps, sets, weight;

    //Empty Constructor (needed to use with the Firebase)
    public Exercise() {

    }

    //Constructor
    public Exercise(String exerciseName, String exerciseBodyPart, String exerciseSets, String exerciseReps, String exerciseWeight) {
        this.name = exerciseName;
        this.bodyPart = exerciseBodyPart;
        this.sets = exerciseSets;
        this.reps = exerciseReps;
        this.weight = exerciseWeight;
    }
    
    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    //An override of the toString() method for formatting and returning the info in a single combined string
    @Override
    public String toString() {
        return "Exercise Name: " + name +
                "\nWeight: " + weight + "KG plus bar" +
                "\nSets:" + sets +
                "\nReps Per Set:" + reps;
    }
}
