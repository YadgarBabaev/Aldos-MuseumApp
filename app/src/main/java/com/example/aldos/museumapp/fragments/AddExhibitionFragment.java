package com.example.aldos.museumapp.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aldos.museumapp.R;
import com.example.aldos.museumapp.dbClasses.DBHelper;

import java.io.ByteArrayOutputStream;

public class AddExhibitionFragment extends Fragment {

    ImageView img;
    EditText title, text;
    byte[] imageByteArray;

    public AddExhibitionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_exhibition, container, false);

        title = (EditText) view.findViewById(R.id.e_title);
        text  = (EditText) view.findViewById(R.id.e_text);

        img = (ImageView) view.findViewById(R.id.e_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.saveBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveAction();}
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 10){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap image = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            imageByteArray = outputStream.toByteArray();

            Bitmap imageRounded = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
            Canvas canvas = new Canvas(imageRounded);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, image.getWidth(), image.getHeight())), 100, 100, paint);// Round Image Corner 100 100 100 100
            img.setImageBitmap(imageRounded);
        }
    }

    private void saveAction(){
        String e_title = title.getText().toString();
        String e_text = text.getText().toString();
        DBHelper db = new DBHelper(getActivity());
        if (!e_title.matches("") && imageByteArray != null) {
            long answer = db.insertExhibition(imageByteArray, e_title, e_text);
            if(answer > 0){
                getActivity().finish();
                startActivity(getActivity().getIntent());
            } else Toast.makeText(getActivity(), "Error occurred while data inserted", Toast.LENGTH_SHORT).show();
        }
    }
}