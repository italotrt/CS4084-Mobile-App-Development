package ie.ul.cs4084.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d("List Activity", "onCreate called");
        if (getIntent() != null) {
            List<Meal> mealList = getIntent().getParcelableArrayListExtra("mealsList");
            RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyRecyclerViewAdapter(this, mealList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void onItemClick(View view, int position) {
        Toast.makeText(this,
                "Calories: " + adapter.getCalories(position) + "\nIngredients: " + adapter.getIngredients(position),
                Toast.LENGTH_SHORT
                ).show();
    }
}
