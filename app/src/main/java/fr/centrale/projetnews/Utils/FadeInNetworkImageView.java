package fr.centrale.projetnews.Utils;

/**
 * Created by gperouffe on 13/01/2018.
 *
 * Code available at https://gist.github.com/benvd/5683818
 * Credits to Github user benvd
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.*;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

import fr.centrale.projetnews.R;

public class FadeInNetworkImageView extends NetworkImageView {

    private static final int FADE_IN_TIME_MS = 250;

    public FadeInNetworkImageView(Context context) {
        super(context);
    }

    public FadeInNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadeInNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new BitmapDrawable(getContext().getResources(), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_book_24dp)),
                new BitmapDrawable(getContext().getResources(), bm)
        });

        setImageDrawable(td);
        td.startTransition(FADE_IN_TIME_MS);
    }
}