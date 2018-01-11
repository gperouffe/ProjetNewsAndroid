package fr.centrale.projetnews.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fr.centrale.projetnews.Fragments.ArticleFragment;
import fr.centrale.projetnews.NewsApplication;
import fr.centrale.projetnews.POJO.NewsArticle;
import fr.centrale.projetnews.R;
import fr.centrale.projetnews.Utils.CustomLruCache;

/**
 * Created by Guillaume on 23/11/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<NewsArticle> articles;
    private SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private DateFormat toLocaleDate = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
    private DateFormat toLocaleTime = new SimpleDateFormat("HH'h'mm");
    private ImageLoader imageLoader;
    private Context context;

    public ArticleAdapter(ArrayList<NewsArticle> articles, Context context) {
        imageLoader = ((NewsApplication)context.getApplicationContext()).getImageLoader();
        this.context = context;
        this.articles = articles;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View view;

        View evenView;
        View oddView;

        TextView title_e;
        TextView date_e;
        TextView author_e;
        NetworkImageView imView_e;

        TextView title_o;
        TextView date_o;
        TextView author_o;
        NetworkImageView imView_o;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            evenView = itemView.findViewById(R.id.even_item);
            oddView = itemView.findViewById(R.id.odd_item);

            title_e = evenView.findViewById(R.id.title);
            date_e = evenView.findViewById(R.id.date);
            author_e = evenView.findViewById(R.id.author);
            imView_e = evenView.findViewById(R.id.imViewAtricle);

            title_o = oddView.findViewById(R.id.title);
            date_o = oddView.findViewById(R.id.date);
            author_o = oddView.findViewById(R.id.author);
            imView_o = oddView.findViewById(R.id.imViewAtricle);
        }

        public void setContentWithPosition(int position, String title, String date, String author, String imgUrl, ImageLoader imgLoader){
            if(position % 2 == 0){
                oddView.setVisibility(View.GONE);

                title_e.setText(title);
                date_e.setText(date);
                author_e.setText(author);
                imView_e.setImageUrl(imgUrl, imgLoader);

                evenView.setVisibility(View.VISIBLE);
            }
            else{
                evenView.setVisibility(View.GONE);

                title_o.setText(title);
                date_o.setText(date);
                author_o.setText(author);
                imView_o.setImageUrl(imgUrl, imgLoader);

                oddView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NewsArticle article = articles.get(position);
        try {
            Resources res = context.getResources();
            Date published = dateParse.parse(article.getPublishedAt());
            String date = res.getString(R.string.publish_date, toLocaleDate.format(published), toLocaleTime.format(published));

            String author = article.getAuthor();
            author = author==null? res.getString(R.string.anonymous): author;

            holder.setContentWithPosition(
                    position,
                    article.getTitle(),
                    date,
                    author,
                    article.getUrlToImage(),
                    imageLoader);

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ArticleFragment.OnFragmentInteractionListener) view.getContext()).onArticleFragmentInteraction(position);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onClick(View view) {

    }
}
