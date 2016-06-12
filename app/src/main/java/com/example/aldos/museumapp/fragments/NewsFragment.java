package com.example.aldos.museumapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aldos.museumapp.R;
import com.example.aldos.museumapp.dbClasses.DBHelper;
import com.example.aldos.museumapp.items.News;
import com.example.aldos.museumapp.items.NewsAdapter;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ArrayList<News> newsArrayList = new ArrayList<>();

    public NewsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        setData();

        ListView list = (ListView)view.findViewById(R.id.list);
        NewsAdapter adapter = new NewsAdapter(getActivity(), newsArrayList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News temp = newsArrayList.get(position);
                Log.d("News: ", String.valueOf(temp.getId()));
//                Toast.makeText(getContext(), temp.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void setData() {
        DBHelper db = new DBHelper(getActivity());
        int[] ids = db.getIds("news");
        ids = reverseArray(ids);
        for (int id : ids){
            News object = db.getNews(id);
            newsArrayList.add(object);
        }
    }

    private int[] reverseArray(int[] array){
        for(int i = 0; i < array.length / 2; i++)
        {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }
}
