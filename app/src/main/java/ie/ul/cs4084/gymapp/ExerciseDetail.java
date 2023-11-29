package ie.ul.cs4084.gymapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetail extends Fragment {
    //Variables created for the recycler view
    private RecyclerView exerciseRV;
    private ArrayList<Exercise> exerciseArrayList;
    private ExerciseRVAdapter exerciseRVAdapter;
    private FirebaseFirestore database;
    ProgressBar loadingPB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Instantiates a new Exercise detail.
     */
    public ExerciseDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseDetail.
     */
// TODO: Rename and change types and number of parameters
    public static ExerciseDetail newInstance(String param1, String param2) {
        ExerciseDetail fragment = new ExerciseDetail();
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
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialising the variables
        exerciseRV = view.findViewById(R.id.idRVExercise);
        loadingPB = view.findViewById(R.id.idProgressBar);
        database = FirebaseFirestore.getInstance();

        //Creating a new array list
        exerciseArrayList = new ArrayList<>();
        exerciseRV.setHasFixedSize(true);
        exerciseRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        exerciseRVAdapter = new ExerciseRVAdapter(exerciseArrayList, this.getContext()); //adding the array list to the recycler view adapter class
        exerciseRV.setAdapter(exerciseRVAdapter); //setting the adapter to the recycler view

        //Gets the data from the Firebase
        database.collection("Exercises").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //Hides the loading bar if the snapshot is not empty
                            loadingPB.setVisibility(View.GONE);

                            //Adding the data to a list
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                //Passes each data from the list to the Exercise class
                                Exercise e = d.toObject(Exercise.class);
                                //Adds the exercises to the array list created for the recycler view
                                exerciseArrayList.add(e);
                            }
                            //Calls the notifyDataSetChanged to notify that the data has been changed in the recycler view
                            exerciseRVAdapter.notifyDataSetChanged();
                        } else {
                            //If there is no data in the database tell the user and return to the previous page
                            Toast.makeText(ExerciseDetail.this.getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Exception made if it fails to get the data from the database, and creates a Toast telling the user that it failed
                        Toast.makeText(ExerciseDetail.this.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}