package com.example.sennevervaecke.mystable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class HorseFragment extends Fragment {

    private HorseComm comm;
    private ArrayList<Horse> horses;
    private ListView listView;
    private HorseAdapter.ButtonAction buttonAction = HorseAdapter.ButtonAction.ADD;
    public HorseFragment() {}

    public void setButtonAction(HorseAdapter.ButtonAction buttonAction){
        this.buttonAction = buttonAction;
    }

    public void setHorses(ArrayList<Horse> horses){
        this.horses = horses;
        if(listView != null){
            if(listView.getAdapter() != null) {
                ((HorseAdapter) listView.getAdapter()).refresh(horses);
            } else {
                listView.setAdapter(new HorseAdapter(getContext(), horses, buttonAction));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_horse, container, false);
        listView = view.findViewById(R.id.horsesList);
        if(horses != null) {
            listView.setAdapter(new HorseAdapter(view.getContext(), horses, buttonAction));
        }
        listView.setOnItemClickListener(new HorseClickListener());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HorseComm) {
            comm = (HorseComm) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        comm = null;
    }

    public class HorseClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            comm.onHorsePressed((Horse) adapterView.getAdapter().getItem(i));
        }
    }

    public interface HorseComm {
        void onHorsePressed(Horse horse);
    }
}
