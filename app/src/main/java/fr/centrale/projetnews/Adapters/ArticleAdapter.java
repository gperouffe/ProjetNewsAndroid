package fr.centrale.projetnews.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
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

/**
 * Created by Guillaume on 23/11/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private ArrayList<NewsArticle> articles;
    private SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private DateFormat toLocaleDate = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
    private DateFormat toLocaleTime = new SimpleDateFormat("HH'h'mm");
    private ImageLoader imageLoader;
    private Context context;
    private RecyclerView rv;

    private final int VIEW_ITEM_EVEN = 0;
    private final int VIEW_ITEM_ODD = 1;
    private final int VIEW_LOADING = 2;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private boolean loading;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public ArticleAdapter(ArrayList<NewsArticle> articles, final Context context, RecyclerView rv) {
        imageLoader = ((NewsApplication) context.getApplicationContext()).getImageLoader();
        this.context = context;
        this.articles = articles;
        this.rv = rv;


        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && lastVisibleItem > visibleThreshold) {
                    loading = true;
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            ((OnLoadMoreListener) context).onLoadMore();
                        }
                    });
                }
            }
        });
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
        View view;

        private TextView title;
        private TextView date;
        private TextView author;
        private NetworkImageView imView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            author = view.findViewById(R.id.author);
            imView = view.findViewById(R.id.imViewAtricle);
            imView.setDefaultImageResId(R.drawable.ic_book_24dp);
            imView.setErrorImageResId(R.drawable.ic_book_24dp);
        }

        public void setContent(String title, String date, String author, String imgUrl, ImageLoader imgLoader){
            this.title.setText(title);
            this.date.setText(date);
            this.author.setText(author);
            this.imView.setImageUrl(imgUrl, imgLoader);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh;
        switch (viewType){
            case VIEW_ITEM_EVEN:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_even, parent, false);
                vh = new ArticleViewHolder(v);
                break;
            case VIEW_ITEM_ODD:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_odd, parent, false);
                vh = new ArticleViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_loading, parent, false);
                vh = new LoadingViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ArticleViewHolder){
            ArticleViewHolder holderCast = (ArticleViewHolder) holder;
            NewsArticle article = articles.get(position);
            Resources res = context.getResources();
            Date published = new Date();
            try {
                published = dateParse.parse(article.getPublishedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String date = res.getString(R.string.publish_date, toLocaleDate.format(published), toLocaleTime.format(published));
            String author = article.getAuthor();
            author = author==null? res.getString(R.string.anonymous): author;

            holderCast.setContent(
                    article.getTitle(),
                    date,
                    author,
                    article.getUrlToImage(),
                    imageLoader);

            holderCast.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ArticleFragment.OnFragmentInteractionListener) view.getContext()).onArticleFragmentInteraction(position);
                }
            });
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getItemViewType(int position) {
        return articles.get(position) != null? position%2: VIEW_LOADING;
    }


    public void loadEnd() {
        this.loading = false;
    }
}
