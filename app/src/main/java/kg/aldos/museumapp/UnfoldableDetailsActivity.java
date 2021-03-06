package kg.aldos.museumapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.bumptech.glide.Glide;
import kg.aldos.museumapp.items.Painting;
import kg.aldos.museumapp.items.PaintingsAdapter;

public class UnfoldableDetailsActivity extends AppCompatActivity {

    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfoldable_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = Views.find(this, R.id.list_view);
        listView.setAdapter(new PaintingsAdapter(this));

        mListTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = Views.find(this, R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = Views.find(this, R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        mUnfoldableView.setFoldShading(new GlanceFoldShading(glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            finish();
        }
    }

    public void openDetails(View coverView, Painting painting) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView image = Views.find(mDetailsLayout, R.id.details_image);
        TextView title = Views.find(mDetailsLayout, R.id.details_title);
        TextView description = Views.find(mDetailsLayout, R.id.details_text);

        Glide.with(this)
                .load(painting.getImg())
                .dontTransform()
                .dontAnimate()
                .into(image);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .append(painting.getAuthor()).append(". ")
                .append(painting.getDate()).append(".\n")
                .append(painting.getText());
        description.setText(builder.build());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public ActionBar getSupportActionBar() {
        // Making getSupportActionBar() method to be @NonNull
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar == null) throw new NullPointerException("Action bar was not initialized");
        return actionBar;
    }

}
