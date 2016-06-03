package com.example.aldos.museumapp.fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.aldos.museumapp.CameraPreview;
import com.example.aldos.museumapp.R;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

public class ScannerFragment extends Fragment {

    private Camera mCamera;
    private Handler autoFocusHandler;
    private FrameLayout preview;
    private TextView scanText;
    private Image codeImage;
    private ImageScanner scanner;
    private boolean previewing = true;

    static {System.loadLibrary("iconv");}

    public ScannerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_scanner, null);

        autoFocusHandler = new Handler();
        preview = (FrameLayout) view.findViewById(R.id.cameraPreview);

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        scanText = (TextView) view.findViewById(R.id.scanText);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try { c = Camera.open();
        } catch (Exception ignored) {}
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.cancelAutoFocus();
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void resumeCamera() {
        scanText.setText("Scanning...");
        mCamera = getCameraInstance();
        CameraPreview mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
//        ((FrameLayout) findViewById(R.id.camera)).addView(mPreview);
        preview.removeAllViews();
        preview.addView(mPreview);
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            codeImage = new Image(size.width, size.height, "Y800");
            previewing = true;
            mPreview.refreshDrawableState();
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing && mCamera != null) {
                mCamera.autoFocus(autoFocusCB);
            }
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            codeImage.setData(data);
            int result = scanner.scanImage(codeImage);
            if (result != 0) {
                SymbolSet symbolSet = scanner.getResults();
                for (Symbol sym : symbolSet) {
                    String lastScannedCode = sym.getData();
                    if (lastScannedCode != null) {
                        scanText.setText("Scanned result: " + lastScannedCode);
                    }
                }
            }
            camera.addCallbackBuffer(data);
        }
    };

    // Mimic continuous auto-focusing
    final Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}