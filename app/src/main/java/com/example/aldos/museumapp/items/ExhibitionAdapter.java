package com.example.aldos.museumapp.items;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aldos.museumapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ExhibitionAdapter extends ArrayAdapter<Exhibition>{

    ArrayList<Exhibition> Data;
    Exhibition object;
    Activity activity;

    public ExhibitionAdapter(Activity activity, ArrayList<Exhibition> data){
        super(activity, R.layout.exhibition_item, data);
        this.activity = activity;
        this.Data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.exhibition_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.text = (TextView) view.findViewById(R.id.text);
            holder.image = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        if (Data.size() <= 0) {
            holder.text.setGravity(View.TEXT_ALIGNMENT_CENTER);
            holder.text.setText("\n No Data \n");
        } else {
            object = Data.get(position);
            holder.title.setText(object.getTitle());
            holder.text.setText(object.getText());

            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(object.getImage()));
            Bitmap imageRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(imageRounded);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight())), 100, 100, paint);
            holder.image.setImageBitmap(bitmap);
        }
        return view;
    }

    static class ViewHolder{
        public TextView title;
        public TextView text;
        public ImageView image;
    }
}
