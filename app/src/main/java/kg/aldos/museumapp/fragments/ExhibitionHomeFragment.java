package kg.aldos.museumapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.aldos.museumapp.R;

import kg.aldos.museumapp.items.ExhibitionAdapter;

public class ExhibitionHomeFragment extends Fragment{

    public ExhibitionHomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibition, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ExhibitionAdapter adapter = new ExhibitionAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        return view;
    }
}
