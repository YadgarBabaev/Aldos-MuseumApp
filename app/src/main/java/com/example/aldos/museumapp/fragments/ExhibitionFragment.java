package com.example.aldos.museumapp.fragments;

import android.graphics.drawable.ColorDrawable;
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
import com.example.aldos.museumapp.items.Exhibition;
import com.example.aldos.museumapp.items.ExhibitionAdapter;

import java.util.ArrayList;

public class ExhibitionFragment extends Fragment {

    ArrayList<Exhibition> arrayList = new ArrayList<>();
    public ExhibitionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibition, container, false);

        setData();

        ListView list = (ListView)view.findViewById(R.id.list);

        ExhibitionAdapter adapter = new ExhibitionAdapter(getActivity(), arrayList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exhibition item = arrayList.get(position);
                Log.d("Exhibition: ", String.valueOf(item));
            }
        });
        return view;
    }

    public void setData() {
        DBHelper db = new DBHelper(getActivity());
        int[] ids = db.getIds("exhibition");
        ids = reverseArray(ids);
        for (int id : ids){
            Exhibition object = db.getExhibition(id);
            arrayList.add(object);
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
