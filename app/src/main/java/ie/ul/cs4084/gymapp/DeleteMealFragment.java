package ie.ul.cs4084.gymapp;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteMealFragment extends Fragment {

    List<Meal> mealList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteMealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteMealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteMealFragment newInstance(String param1, String param2) {
        DeleteMealFragment fragment = new DeleteMealFragment();
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
        return inflater.inflate(R.layout.fragment_delete_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState) {
        super.onViewCreated(view, SavedInstanceState);

        MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        mealList = mealViewModel.getMealsLiveData().getValue();

        Button deleteButton = view.findViewById(R.id.deleteItem);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText itemNameInput = getView().findViewById(R.id.itemName2);
                String itemName = itemNameInput.getText().toString();

                boolean itemRemoved = false;
                if (mealList == null || mealList.isEmpty()) {
                    Log.d("Delete Item", "There are no items to delete!");
                } else {
                    for (int i = 0; i < mealList.size(); i++) {
                        if (mealList.get(i).getName().equals(itemName)) {
                            mealList.remove(i);
                            itemRemoved = true;
                            Log.d("Delete Item", itemName + " removed.");
                            break;
                        }
                    }
                    if (!itemRemoved) {
                        Log.d("Delete Item", "No item was found.");
                    }
                }
            }
        });

        Button backButton = view.findViewById(R.id.backToMealPlanner2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });
    }

    public void onStop() {
        super.onStop();
        MealViewModel mealViewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        mealViewModel.setMeals(mealList);
    }
}
