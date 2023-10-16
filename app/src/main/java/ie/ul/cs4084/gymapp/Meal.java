package ie.ul.cs4084.gymapp;

import java.util.ArrayList;

public class Meal {
    String name;
    int calories;
    ArrayList<String> ingredients;

    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public String getIngredients() {
        String s = "";
        for(int i = 0; i < ingredients.size(); i++){
            s += ingredients.get(i) + " ";
        }
        return s;
    }

    public void addIngredient(String s){
        ingredients.add(s);
    }

    @Override
    public String toString(){
        return "Name: " + name + "\n Ingredients: " + getIngredients() +"\nCalories: " + calories;
    }
}
