package com.example.cs4084_project;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

//A class for a Meal that will be used in the Meal Planner
public class Meal implements Parcelable {
    String name;//Name of Meal
    int calories; //Number of calories in the Meal
    ArrayList<String> ingredients; //An ArrayList of Strings storing the ingredients used

    //Constructor for a Meal
    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public Meal(String name, int calories, ArrayList<String> ingredients) {
        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;
    }

    protected Meal(Parcel in) {
        name = in.readString();
        calories = in.readInt();
        ingredients = in.createStringArrayList();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };
    
    //Getter for the name variable (Returns String)
    public String getName() {
        return name;
    }

    //Getter for the calories variable (Returns int)
    public int getCalories() {
        return calories;
    }

    //Getter for every ingredient in the ingredients ArrayList (Returns a single String)
    public String getIngredients() {
        String s = "";
        for(int i = 0; i < ingredients.size(); i++){
            s += ingredients.get(i) + " ";
        }
        return s;
    }

    //Add an ingredient to the ingredients ArrayList
    public void addIngredient(String s){
        ingredients.add(s);
    }

    //A toString method returning the Meal info
    @Override
    public String toString(){
        return "Name: " + name + "\nIngredients: " + getIngredients() +"\nCalories: " + calories;
    }

     @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(calories);
        dest.writeStringList(ingredients);
    }
}
