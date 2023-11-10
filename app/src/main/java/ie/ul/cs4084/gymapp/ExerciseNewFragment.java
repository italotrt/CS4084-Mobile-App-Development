package ie.ul.cs4084.gymapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseNewFragment extends Fragment {
    private TextInputLayout textInputExerciseName;
    private TextInputLayout textInputBodyPart;
    private TextInputLayout numberInputWeight;
    private TextInputLayout numberInputSets;
    private TextInputLayout numberInputReps;
    private FirebaseFirestore db;

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

        textInputExerciseName = view.findViewById(R.id.text_input_exercise_name);
        textInputBodyPart = view.findViewById(R.id.text_input_body_part);
        numberInputWeight = view.findViewById(R.id.number_input_weight);
        numberInputSets = view.findViewById(R.id.number_input_sets);
        numberInputReps = view.findViewById(R.id.number_input_reps);
        db = FirebaseFirestore.getInstance();
        exercises = db.collection("Exercises");

        Button addExercise = view.findViewById(R.id.btnAddNewExercise);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validation = validateExerciseName() && validateBodyPart() && validateWeight() && validateSets() && validateReps();

                if(validation) {
                    Map<String, Object> exercise1 = new HashMap<>();
                    exercise1.put("Name", textInputExerciseName);
                    exercise1.put("Body Part", textInputBodyPart);
                    exercise1.put("Weight", numberInputWeight);
                    exercise1.put("Sets", numberInputSets);
                    exercise1.put("Reps", numberInputReps);
                    exercises.document(textInputExerciseName.getEditText().getText().toString()).set(exercise1);

                    getActivity().onBackPressed();
                }
            }
        });
    }

    private boolean validateExerciseName() {
        String exerciseNameInput = textInputExerciseName.getEditText().getText().toString();

        if (exerciseNameInput.isEmpty()) {
            textInputExerciseName.setError("Field can't be empty");
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
            return false;
        } else {
            textInputBodyPart.setError(null);
            return true;
        }
    }

    private boolean validateWeight() {
        String weightInput = numberInputWeight.getEditText().getText().toString();

        if (weightInput.isEmpty()) {
            numberInputWeight.setError("Field can't be empty");
            return false;
        } else {
            numberInputWeight.setError(null);
            return true;
        }
    }

    private boolean validateSets() {
        String setsInput = numberInputSets.getEditText().getText().toString();

        if (setsInput.isEmpty()) {
            numberInputSets.setError("Field can't be empty");
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
            return false;
        } else {
            numberInputReps.setError(null);
            return true;
        }
    }
}
