package kg.aldos.museumapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.bumptech.glide.Glide;
import kg.aldos.museumapp.R;

import kg.aldos.museumapp.dbClasses.DBHelper;
import kg.aldos.museumapp.items.Painting;

public class PictureDetailsFragment extends Fragment {

    private int id;

    public static PictureDetailsFragment newInstance(int id) {
        Bundle args=new Bundle();
        args.putInt("ID", id);
        PictureDetailsFragment fragment = new PictureDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("ID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_details, null);

        DBHelper dbHelper = new DBHelper(getActivity());
        Painting item = dbHelper.getPainting(id);
        if (item != null) {
            ImageView image = (ImageView)view.findViewById(R.id.details_image);
            TextView title = (TextView)view.findViewById(R.id.details_title);
            TextView description = (TextView)view.findViewById(R.id.details_text);

            getActivity().setTitle(item.getTitle() + " " + item.getDate());
            Glide.with(this)
                    .load(item.getImg())
                    .dontTransform()
                    .dontAnimate()
                    .into(image);
            title.setText(item.getTitle());

            SpannableBuilder builder = new SpannableBuilder(getActivity());
            builder
                    .append(item.getAuthor()).append(". ")
                    .append(item.getDate()).append(".\n")
                    .append(item.getText());
            description.setText(builder.build());
        }
        return view;
    }
}
