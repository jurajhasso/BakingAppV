package com.example.juraj.bakingappv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juraj.bakingappv.model.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    static ArrayList<Step> mStepsX = new ArrayList<Step>();

    public static final Map<String, Step> STEP_MAP = new HashMap<String, Step>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }

        ArrayList<Step> mSteps = getIntent().getExtras().getParcelableArrayList("Steps");

        mStepsX = mSteps;

        Log.i("StepListActivity", mSteps + " steps loaded.");

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mStepsX, mTwoPane));

        Log.i("StepListActivity2222", "Step while setting up" + mStepsX);

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final ArrayList<Step> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step step = (Step) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();

                    arguments.putParcelableArrayList("STEPS", mStepsX);
                    arguments.putInt("id", step.id);
                    arguments.putString("shortDescription", step.shortDescription);
                    arguments.putString("description", step.description);
                    arguments.putString("videoURL", step.videoURL);
                    arguments.putString("thumbnailURL", step.thumbnailURL);

                    StepDetailFragment fragment = new StepDetailFragment();

                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);

                    intent.putParcelableArrayListExtra("STEPS", mStepsX);
                    intent.putExtra("id", step.id);
                    intent.putExtra("shortDescription", step.shortDescription);
                    intent.putExtra("description", step.description);
                    intent.putExtra("videoURL", step.videoURL);
                    intent.putExtra("thumbnailURL", step.thumbnailURL);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(StepListActivity parent,
                                      ArrayList<Step> steps,
                                      boolean twoPane) {
            mValues = steps;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mIdView.setText(mValues.get(position).id.toString());
   //         holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
