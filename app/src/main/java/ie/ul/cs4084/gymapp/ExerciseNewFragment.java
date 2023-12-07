package ie.ul.cs4084.gymapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseNewFragment extends Fragment {
    // Variables used in order to add the data to the database, and get user's input
    private TextInputLayout textInputExerciseName, textInputBodyPart, numberInputWeight, numberInputSets, numberInputReps;
    private String exerciseName, exerciseBodyPart, exerciseSets, exerciseReps, exerciseWeight;
    private FirebaseFirestore database;
    private CollectionReference exercises;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExerciseNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseNewFragment newInstance(String param1, String param2) {
        ExerciseNewFragment fragment = new ExerciseNewFragment();
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
        return inflater.inflate(R.layout.fragment_exercise_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initializes the variables for the TextInputLayout
        textInputExerciseName = view.findViewById(R.id.text_input_exercise_name);
        textInputBodyPart = view.findViewById(R.id.text_input_body_part);
        numberInputWeight = view.findViewById(R.id.number_input_weight);
        numberInputSets = view.findViewById(R.id.number_input_sets);
        numberInputReps = view.findViewById(R.id.number_input_reps);
        database = FirebaseFirestore.getInstance();
        exercises = database.collection("Exercises"); // Gets the reference for the Exercises collection in the database

        Button addExercise = view.findViewById(R.id.btnAddNewExercise);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validation = validateExerciseName() && validateBodyPart() && validateSets() && validateReps();

                // If it passes the validation, execute the following code
                if(validation) {
                    // Gets the user's input and assign it to the respective variable
                    exerciseName = textInputExerciseName.getEditText().getText().toString();
                    exerciseBodyPart = "Body Part: " + textInputBodyPart.getEditText().getText().toString();
                    exerciseSets = "Sets: " + numberInputSets.getEditText().getText().toString();
                    exerciseReps = "Reps: " + numberInputReps.getEditText().getText().toString();

                    // If the user did not input a weight, it assigns it to zero,
                    // otherwise assign what the user has inputted
                    if(numberInputWeight.getEditText().getText().toString().isEmpty()) {
                        exerciseWeight = "Weight: 0 kg";
                    } else {
                        exerciseWeight = "Weight: " + numberInputWeight.getEditText().getText().toString() + " kg";
                    }

                    // Creates an Exercise object with the user's input
                    Exercise exercise = new Exercise(exerciseName, exerciseBodyPart, exerciseSets, exerciseReps, exerciseWeight);

                    // Creates a new variable and assign the reference for the Exercises collection inside the database to it
                    CollectionReference dbExercises = database.collection("Exercises");
                    dbExercises.add(exercise); //adds the data to the database

                    // A toast is shown in order to let the user know that the exercise was successfully created
                    Toast.makeText(ExerciseNewFragment.this.getContext(), "Exercise successfully created", Toast.LENGTH_SHORT).show();

                    // After everything is done, returns to the previous fragment
                    getActivity().onBackPressed();
                }
            }
        });

        // Button created to return to the previous fragment
        // if the user wish to cancel the creation of a new exercise
        Button cancel = view.findViewById(R.id.btnCancelExercise);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    // Validation methods created to verify the user's input,
    // if the user does not fill the text fields it creates a Toast letting the user know what's wrong

    private boolean validateExerciseName() {
        String exerciseNameInput = textInputExerciseName.getEditText().getText().toString();

        if (exerciseNameInput.isEmpty()) {
            textInputExerciseName.setError("Field can't be empty");
            Toast.makeText(ExerciseNewFragment.this.getContext(), "Insert an exercise name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            textInputExerciseName.setError(null);
            return true;
        }
    }

    private boolean validateBodyPart() {
        String bodyPartInput = textInputBodyPart.getEditText().getText().toString();

        if (bodyPartInput.isEmpty()) {
            textInputBodyPart.setError("Field can't be empty");
            Toast.makeText(ExerciseNewFragment.this.getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            textInputBodyPart.setError(null);
            return true;
        }
    }

    private boolean validateSets() {
        String setsInput = numberInputSets.getEditText().getText().toString();

        if (setsInput.isEmpty()) {
            numberInputSets.setError("Field can't be empty");
            Toast.makeText(ExerciseNewFragment.this.getContext(), "Insert the amount of SETS", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            numberInputSets.setError(null);
            return true;
        }
    }

    private boolean validateReps() {
        String repsInput = numberInputReps.getEditText().getText().toString();

        if (repsInput.isEmpty()) {
            numberInputReps.setError("Field can't be empty");
            Toast.makeText(ExerciseNewFragment.this.getContext(), "Insert the amount of REPS", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            numberInputReps.setError(null);
            return true;
        }
    }
}
