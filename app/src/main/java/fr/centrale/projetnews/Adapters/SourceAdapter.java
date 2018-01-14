package fr.centrale.projetnews.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.centrale.projetnews.Activities.NewsActivity;
import fr.centrale.projetnews.Fragments.SourceFragment;
import fr.centrale.projetnews.POJO.NewsSource;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

/**
 * Created by Guillaume on 25/11/2017.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<NewsSource> sources;

    public SourceAdapter(ArrayList<NewsSource> sources) {
        this.sources = sources;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public SourceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsSource source = sources.get(position);
        holder.name.setText(source.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SourceFragment.OnFragmentInteractionListener)view.getContext()).onSourceFragmentInteraction(source.getId());
            }
        });

        if(source.isSelected()){
            holder.view.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        else{
            holder.view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    @Override
    public void onClick(View view) {

    }
}
