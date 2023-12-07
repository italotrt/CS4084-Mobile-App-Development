package com.example.myfirstfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMealFragment extends Fragment {

    List<Meal> mealList = new ArrayList<Meal>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddMealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMealFragment newInstance(String param1, String param2) {
        AddMealFragment fragment = new AddMealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        if (mealViewModel.getMealsLiveData().getValue() != null) {
            mealList = mealViewModel.getMealsLiveData().getValue();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addmeal, container, false);
    }

    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState) {
        super.onViewCreated(view, SavedInstanceState);

        Button addItemButton = view.findViewById(R.id.addItem);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMeal();
            }
        });

        Button backButton = view.findViewById(R.id.backToMealPlanner);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        mealViewModel.setMeals(mealList);
    }

    public void AddMeal() {

        EditText itemNameInput = getView().findViewById(R.id.itemName);
        EditText caloriesInput = getView().findViewById(R.id.calories);
        EditText ingredientsInput = getView().findViewById(R.id.ingredients);

        String itemName = itemNameInput.getText().toString();
        int calories;
        try {
            calories = Integer.parseInt(caloriesInput.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("Add Meal Calories", "Invalid calories input.");
            return;
        }
        String[] ingredientsArray = ingredientsInput.getText().toString().split(",");
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(ingredientsArray));

        for (Meal meal : mealList) {
            if (meal.getName().equals(itemName)) {
                Log.d("Add Meal Loop", "Item is already in meal! Try a different name.");
                return;
            }
        }
        Meal newMeal = new Meal(itemName, calories, ingredients);
        mealList.add(newMeal);

    }
}