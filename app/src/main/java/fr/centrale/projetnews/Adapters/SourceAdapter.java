package fr.centrale.projetnews.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fr.centrale.projetnews.POJO.NewsArticle;
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
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public SourceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_source, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(sources.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    @Override
    public void onClick(View view) {

    }
}
