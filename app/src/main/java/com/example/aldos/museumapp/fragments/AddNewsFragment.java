package com.example.aldos.museumapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aldos.museumapp.R;
import com.example.aldos.museumapp.dbClasses.DBHelper;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AddNewsFragment extends Fragment {

    EditText n_date, n_text;
    ImageView n_img;
    byte[] imageByteArray;

    public AddNewsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_news, container, false);

        n_date = (EditText)view.findViewById(R.id.news_date);
        n_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        n_img = (ImageView)view.findViewById(R.id.news_image);
        n_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        });
        n_text = (EditText)view.findViewById(R.id.news_text);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.saveBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveAction();}
        });

        return view;
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MMMM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        n_date.setText(sdf.format(myCalendar.getTime()));
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
            n_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            n_img.setImageBitmap(image);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            imageByteArray = outputStream.toByteArray();
        }
    }

    private void saveAction(){
        String date = n_date.getText().toString();
        String text = n_text.getText().toString();
        if (!date.matches("") && !text.matches("") && imageByteArray != null){
            DBHelper db = new DBHelper(getActivity());
            long answer = db.insertNews(date, text, imageByteArray);
            if(answer > 0){
                getActivity().finish();
                startActivity(getActivity().getIntent());
            } else Toast.makeText(getActivity(), "Error occurred while data inserted", Toast.LENGTH_SHORT).show();
        } else {Snackbar.make(getView(), "Error is found", Snackbar.LENGTH_SHORT).show();}
    }
}