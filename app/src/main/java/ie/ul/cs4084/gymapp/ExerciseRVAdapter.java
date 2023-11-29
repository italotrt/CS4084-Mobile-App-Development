package ie.ul.cs4084.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//This class was originally from https://www.geeksforgeeks.org/how-to-read-data-from-firebase-firestore-in-android/
//But it was implemented to suit the project

//An adapter class made to be used with the RecyclerView,
// which will handle the data items in the RecyclerView
public class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ViewHolder> {
    //Variables for the ArrayList and Context
    private ArrayList<Exercise> exercisesArrayList;
    private Context context;

    //Constructor for the class
    public ExerciseRVAdapter(ArrayList<Exercise> exercisesArrayList, Context context) {
        this.exercisesArrayList = exercisesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Passes exercise_item.xml layout file for displaying the card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Exercise exercises = exercisesArrayList.get(position);
        holder.exerciseName.setText(exercises.getName());
        holder.exerciseBodyPart.setText(exercises.getBodyPart());
        holder.exerciseSets.setText(exercises.getSets());
        holder.exerciseReps.setText(exercises.getReps());
        holder.exerciseWeight.setText(exercises.getWeight());
    }

    //Getter for the size of the ArrayList
    @Override
    public int getItemCount() {
        return exercisesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Variables for the text views
        private final TextView exerciseName, exerciseBodyPart, exerciseSets, exerciseReps, exerciseWeight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Initializing the variables for the TextView
            exerciseName = itemView.findViewById(R.id.idExerciseName);
            exerciseBodyPart = itemView.findViewById(R.id.idExerciseBodyPart);
            exerciseSets = itemView.findViewById(R.id.idExerciseSets);
            exerciseReps = itemView.findViewById(R.id.idExerciseReps);
            exerciseWeight = itemView.findViewById(R.id.idExerciseWeight);
        }
    }
}

