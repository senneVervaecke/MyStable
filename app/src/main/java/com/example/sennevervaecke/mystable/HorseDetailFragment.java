package com.example.sennevervaecke.mystable;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HorseDetailFragment extends DialogFragment {

    private Horse horse;

    public HorseDetailFragment() {
        // Required empty public constructor
    }
    public void setHorse(Horse horse){
        this.horse = horse;
    }
    public Horse getHorse(){
        return horse;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horse_detail, container, false);
        ((TextView) view.findViewById(R.id.HorseDetailTitle)).setText(horse.getName());
        ((TextView) view.findViewById(R.id.HorseDetailAge)).setText(String.valueOf(horse.getYear()));
        ((TextView) view.findViewById(R.id.HorseDetailFather)).setText(Helper.toTitleCase(getString(R.string.fatherPrefix) + horse.getFather()));
        ((TextView) view.findViewById(R.id.HorseDetailMother)).setText(Helper.toTitleCase(getString(R.string.motherPrefix) + horse.getMother()));
        ((TextView) view.findViewById(R.id.HorseDetailFatherMother)).setText(Helper.toTitleCase(getString(R.string.motherFatherPrefix) + horse.getFatherMother()));

        return view;
    }
}
