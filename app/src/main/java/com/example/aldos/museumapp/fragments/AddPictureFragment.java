package com.example.aldos.museumapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldos.museumapp.dbClasses.DBHelper;
import com.example.aldos.museumapp.R;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class AddPictureFragment extends Fragment {

    byte[] imageByteArray;
    TextView pict;
    EditText name, author, year, text;

    public AddPictureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_picture, container, false);

        name   = (EditText)view.findViewById(R.id.name);
        author = (EditText)view.findViewById(R.id.author);
        year   = (EditText)view.findViewById(R.id.year);
        text   = (EditText)view.findViewById(R.id.description);
        pict   = (TextView)view.findViewById(R.id.imgByteArray);

        Button imgSelect  = (Button)view.findViewById(R.id.selectImg);
        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.saveBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
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
            pict.setText(Arrays.toString(imageByteArray).substring(0, 20) + "...");
            pict.setError(null);
        }
    }

    public void saveAction() {
        String s_name = name.getText().toString();
        String s_author = author.getText().toString();
        String s_date = year.getText().toString();
        String s_text = text.getText().toString();

        if (s_name.matches(""))
            name.setError("Required field!");
        else if (pict.getText().equals(""))
            pict.setError("Please, select picture");
        else{
            DBHelper db = new DBHelper(getActivity());
            long answer = db.insertPicture(s_name, s_text, s_author, s_date, imageByteArray);
            if(answer > 0){
                getActivity().finish();
                startActivity(getActivity().getIntent());
            } else Toast.makeText(getActivity(), "Error occurred while data inserted", Toast.LENGTH_SHORT).show();

        }
    }

}
