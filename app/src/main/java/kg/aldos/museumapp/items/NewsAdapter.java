package kg.aldos.museumapp.items;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kg.aldos.museumapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>{

    ArrayList<News> Data;
    News news;
    Activity activity;

    public NewsAdapter(Activity activity, ArrayList<News> data){
        super(activity, R.layout.news_item, data);
        this.activity = activity;
        this.Data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.news_item, null);
            holder = new ViewHolder();
            holder.date = (TextView) view.findViewById(R.id.news_date);
            holder.text = (TextView) view.findViewById(R.id.news_text);
            holder.image = (ImageView) view.findViewById(R.id.news_image);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        if (Data.size() <= 0) {
            holder.text.setGravity(View.TEXT_ALIGNMENT_CENTER);
            holder.text.setText("\n No Data \n");
        } else {
            news = Data.get(position);
            holder.date.setText(news.getDate());
            holder.text.setText(news.getText());
            Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(news.getCover()));
            holder.image.setImageBitmap(bitmap);
        }
        return view;
    }

    static class ViewHolder{
        public TextView date;
        public TextView text;
        public ImageView image;
    }
}
