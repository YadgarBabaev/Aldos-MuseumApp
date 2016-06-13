package kg.aldos.museumapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kg.aldos.museumapp.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import kg.aldos.museumapp.items.Exhibition;

public class ExhibitionFragment extends Fragment {

    private int pos;
    private static String POS = "pos";
    private static ArrayList<Exhibition> arrayList;
//    private ArrayList<Exhibition> arrayList = new ArrayList<>();

    public static ExhibitionFragment newInstance(int position, ArrayList<Exhibition> data) {
        Bundle args = new Bundle();
        args.putInt(POS, position);
        arrayList = data;
        ExhibitionFragment fragment = new ExhibitionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt(POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exhibition_item, container, false);
        ImageView cover = (ImageView)view.findViewById(R.id.imageView);
        TextView title  = (TextView)view.findViewById(R.id.title);
        TextView text   = (TextView)view.findViewById(R.id.text);

        Exhibition item = arrayList.get(pos);
        title.setText("\t" + item.getTitle());
        text.setText(item.getText());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(item.getImage());
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        cover.setImageBitmap(bitmap);
        return view;
    }
}
