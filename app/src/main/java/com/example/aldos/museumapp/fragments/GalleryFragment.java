package com.example.aldos.museumapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.bumptech.glide.Glide;
import com.example.aldos.museumapp.R;
import com.example.aldos.museumapp.items.Painting;
import com.example.aldos.museumapp.items.PaintingsAdapter;

public class GalleryFragment extends Fragment {

    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    public GalleryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_unfoldable_details, container, false);
        ListView listView = Views.find(getActivity(), R.id.list_view);
        listView.setAdapter(new PaintingsAdapter(getActivity()));

        mListTouchInterceptor = Views.find(getActivity(), R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = Views.find(getActivity(), R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = Views.find(getActivity(), R.id.unfoldable_view);

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
        return view;
    }

    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
//        } else {finish();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView image = Views.find(mDetailsLayout, R.id.details_image);
        TextView title = Views.find(mDetailsLayout, R.id.details_title);
        TextView description = Views.find(mDetailsLayout, R.id.details_text);

        Glide.with(this)
                .load(painting.getImg())
                .dontTransform()
                .dontAnimate()
                .into(image);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(getActivity());
        builder
                .append(painting.getAuthor()).append("\n")
                .append(painting.getDate()).append("\n")
                .append(painting.getText());
        description.setText(builder.build());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @NonNull
//    @Override
//    public ActionBar getSupportActionBar() {
//        // Making getSupportActionBar() method to be @NonNull
//        ActionBar actionBar = super.getSupportActionBar();
//        if (actionBar == null) throw new NullPointerException("Action bar was not initialized");
//        return actionBar;
//    }
}