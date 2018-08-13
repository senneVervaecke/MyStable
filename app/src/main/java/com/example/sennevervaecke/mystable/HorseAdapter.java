package com.example.sennevervaecke.mystable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sennevervaecke on 7/27/2018.
 */

public class HorseAdapter extends BaseAdapter {

    public enum ButtonAction{ADD, REMOVE}

    ArrayList<Horse> horses;
    Context context;
    ButtonAction buttonAction;
    ArrayList<Horse> savedHorses;

    public HorseAdapter(Context context, ArrayList<Horse> horses, ButtonAction buttonAction){
        this.context = context;
        this.horses = horses;
        this.buttonAction = buttonAction;
        Iterator<Horse> horseIterator = Horse.findAll(Horse.class);
        savedHorses = new ArrayList<>();
        while (horseIterator.hasNext()){
            savedHorses.add(horseIterator.next());
        }
    }
    @Override
    public int getCount() {
        return horses.size();
    }

    @Override
    public Object getItem(int i) {
        return horses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void refresh(ArrayList<Horse> horses){
        this.horses = horses;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.horse_listview, null);
        ((TextView) view.findViewById(R.id.listHorseName)).setText(Helper.toTitleCase(horses.get(i).getName()));
        ((TextView) view.findViewById(R.id.listHorseYear)).setText(String.valueOf(horses.get(i).getYear()));
        Button action = view.findViewById(R.id.listHorseButton);
        if(buttonAction == ButtonAction.ADD){
            String[] args = {horses.get(i).getName(), horses.get(i).getReg()};
            if(Horse.count(Horse.class, "name = ? and reg = ?", args) > 0){
                action.setText(R.string.remove);
                action.setOnClickListener(new OnRemovePressed(horses.get(i)));
            } else {
                action.setText(R.string.add);
                action.setOnClickListener(new OnAddPressed(horses.get(i)));
            }
        } else if(buttonAction == ButtonAction.REMOVE){
            action.setText(R.string.remove);
            action.setOnClickListener(new OnRemovePressed(horses.get(i)));
        }
        return view;
    }
    private class OnAddPressed implements View.OnClickListener{
        private Horse horse;
        public OnAddPressed(Horse horse){
            this.horse = horse;
        }
        @Override
        public void onClick(View view) {
                horse.save();
                savedHorses.add(horse);
                notifyDataSetChanged();
        }
    }
    private class OnRemovePressed implements View.OnClickListener{
        private Horse horse;
        public OnRemovePressed(Horse horse){
            this.horse = horse;
        }
        @Override
        public void onClick(View view) {
            horses.remove(horse);
            horse.delete();
            HorseAdapter.this.notifyDataSetChanged();
        }
    }
}
