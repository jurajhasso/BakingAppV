package com.example.juraj.bakingappv;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.juraj.bakingappv.model.Ingredient;
import com.example.juraj.bakingappv.model.Recipe;
import com.example.juraj.bakingappv.model.Step;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";

    public ArrayList<Recipe> mRecipe;

    private Gson gson;

    private static final String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RequestQueue requestQueue;

    public static final ArrayList<Recipe> RECIPES = new ArrayList<Recipe>();

    Context context = RecipeListActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        requestQueue = Volley.newRequestQueue(this);

        fetchPosts();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Recipe> recipes = Arrays.asList(gson.fromJson(response, Recipe[].class));

            mAdapter = new RecipeAdapter((Activity) context, recipes);
            mRecyclerView.setAdapter(mAdapter);

            Log.i("RecipeActivity", recipes.size() + " recipes loaded.");

            for (Recipe recipe : recipes) {
                Log.i("RecipeActivity", recipe.id + ": " + recipe.name);

                List<Step> mRecipeSteps = recipe.getSteps();

                List<Ingredient> mRecipeIngredients = recipe.getIngredients();
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("RecipeActivity", error.toString());
        }
    };

}