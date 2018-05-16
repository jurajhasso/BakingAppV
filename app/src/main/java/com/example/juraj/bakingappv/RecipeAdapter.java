package com.example.juraj.bakingappv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juraj.bakingappv.model.Recipe;
import com.example.juraj.bakingappv.model.Step;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

     private List<Recipe> recipeList;

        private Context context;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.recipe_name);
            }
        }

        public RecipeAdapter(Activity context, List<Recipe> recipesList) {
            this.recipeList = recipesList;

            this.context = context;

        }

        @Override
        public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_card, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final Recipe recipe = recipeList.get(position);

            holder.mTextView.setText(recipe.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Step> mRecipeSteps = (ArrayList<Step>) recipe.getSteps();

                    Context context = view.getContext();

                    Bundle bundle = new Bundle();

                    Intent intent = new Intent(context, StepListActivity.class);

                    intent.putExtra("Steps", mRecipeSteps);

                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return recipeList.size();
        }
    }