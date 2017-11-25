package fr.centrale.projetnews.Adapters;

import android.content.Context;
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
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.Consts;

/**
 * Created by Guillaume on 23/11/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<NewsArticle> articles;
    private SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private DateFormat toLocaleDate = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
    private DateFormat toLocaleTime = DateFormat.getTimeInstance( DateFormat.SHORT, Locale.FRANCE);

    public ArticleAdapter(ArrayList<NewsArticle> articles) {
        Log.d(Consts.TAG, articles.size() + "");
        this.articles = articles;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            date = itemView.findViewById(R.id.article_date);
            author = itemView.findViewById(R.id.article_author);
        }
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(articles.get(position).getTitle());

        try {
            Date published = dateParse.parse(articles.get(position).getPublishedAt());
            holder.date.setText("Publié le " + toLocaleDate.format(published) + " à " + toLocaleTime.format(published));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.author.setText(articles.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onClick(View view) {

    }
}
