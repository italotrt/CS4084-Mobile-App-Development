package com.example.cs4084_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {

    TextView welcomeText;
    Button meal, exercise, maps;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
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
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String bing = getArguments().getString("name");
        welcomeText = view.findViewById(R.id.welc);
        welcomeText.setText("Welcome, " + bing);
        meal = view.findViewById(R.id.meal);
        exercise = view.findViewById(R.id.exercise);
        maps = view.findViewById(R.id.gymNear);

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_mealPlanFragment);
            }
        });

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_exercisePlannerFragment);
            }
        });
    }
}