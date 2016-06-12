package com.example.aldos.museumapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aldos.museumapp.R;
import com.example.aldos.museumapp.zoom.ImageZoomView;
import com.example.aldos.museumapp.zoom.SimpleZoomListener;
import com.example.aldos.museumapp.zoom.ZoomState;

public class PlanZalaFragment extends Fragment {

    private ImageZoomView mZoomView;
    private ZoomState mZoomState;
    private Bitmap mBitmap;
    float mX = 0;
    float mY = 0;

    public PlanZalaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planzala, container, false);
        mZoomState = new ZoomState();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.museum_map);

        SimpleZoomListener mZoomListener = new SimpleZoomListener();
        mZoomListener.setZoomState(mZoomState);

        mZoomView = (ImageZoomView)view.findViewById(R.id.zoomview);
        mZoomView.setZoomState(mZoomState);
        mZoomView.setImage(mBitmap);
//        mZoomListener.setControlType(SimpleZoomListener.ControlType.PAN);
        mZoomView.setOnTouchListener(mZoomListener);

        ImageButton zoomIn = (ImageButton)view.findViewById(R.id.zoomIn);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        ImageButton zoomOut = (ImageButton)view.findViewById(R.id.zoomOut);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        resetZoomState();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBitmap.recycle();
        mZoomView.setOnTouchListener(null);
        mZoomState.deleteObservers();
    }

    private void resetZoomState() {
        mZoomState.setPanX(0.5f);
        mZoomState.setPanY(0.5f);
        mZoomState.setZoom(1f);
        mZoomState.notifyObservers();
    }
}
