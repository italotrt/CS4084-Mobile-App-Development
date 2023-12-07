package com.example.myfirstfragment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Meal> mealList;
    private LayoutInflater myInflater;
    private static OnItemClickListener myItemClickListener;

    public MyRecyclerViewAdapter (Context context, List<Meal> mealList) {
        this.mealList = mealList;
        this.myInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = myInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        String name = mealList.get(position).getName();
        holder.myTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public int getCalories(int position) {
        return mealList.get(position).getCalories();
    }

    public String getIngredients(int position) {
        return mealList.get(position).getIngredients();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.Name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                myItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick (View view, int position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        myItemClickListener = itemClickListener;
    }
}
