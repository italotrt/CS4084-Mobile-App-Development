package ie.ul.cs4084.gymapp;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealPlanner#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MealPlanner extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MealPlanner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealPlanner newInstance(String param1, String param2) {
        MealPlanner fragment = new MealPlanner();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mealplanner, container, false);
    }

    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState) {
        super.onViewCreated(view, SavedInstanceState);
        Log.d("View Created", "onViewCreated: ");

        MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        if (mealViewModel.getMealsLiveData().getValue() != null) {
            for (Meal meal : mealViewModel.getMealsLiveData().getValue()) {
                Log.d("Meal Planner", "Meal: \n" + meal);
            }
        } else {
            Log.d("Meal Planner", "onViewCreated: There are no meals.");
        }

        Button mealButton = view.findViewById(R.id.CurrentMealButton);
        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Meal> mealList = mealViewModel.getMealsLiveData().getValue();
                if (mealList != null && !mealList.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), ListActivity.class);
                    intent.putParcelableArrayListExtra("mealsList", new ArrayList<>(mealList));
                    startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(),
                            "There are no meals.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button AddButton = view.findViewById(R.id.AddButton);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mealPlanner_to_addmealfragment);
            }
        });

        Button DeleteButton = view.findViewById(R.id.DeleteButton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mealPlanner_to_deleteMealFragment);
            }
        });

        Button clearButton = view.findViewById(R.id.ClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
                List<Meal> mealList = new ArrayList<>();
                mealViewModel.setMeals(mealList);
                Log.d("Clear Button", "Meals cleared!");
            }
        });

        Button homeButton = view.findViewById(R.id.HomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

    }
    }
