package com.example.myfirstfragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MealViewModel extends ViewModel {
    private MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

    public LiveData<List<Meal>> getMealsLiveData() {
        return mealsLiveData;
    }

    public void setMeals(List<Meal> meals) {
        mealsLiveData.setValue(meals);
    }

    public int caloriesTotal() {
        List<Meal> mealList = mealsLiveData.getValue();
        int caloriesTotal = 0;
        for (Meal meal : mealList) {
            caloriesTotal += meal.getCalories();
        }
        return caloriesTotal;
    }
}