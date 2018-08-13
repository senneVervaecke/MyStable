package com.example.sennevervaecke.mystable;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StableActivity extends AppCompatActivity implements HorseFragment.HorseComm {

    private HorseFragment horseFragment;
    private HorseDetailFragment horseDetailFragment;
    private ArrayList<Horse> horses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable);
        if(savedInstanceState == null) {
            horseFragment = new HorseFragment();
            Iterator<Horse> horseList = Horse.findAll(Horse.class);
            horses = new ArrayList<>();
            while(horseList.hasNext()){
                horses.add(horseList.next());
            }
            horseFragment.setHorses(horses);
            horseFragment.setButtonAction(HorseAdapter.ButtonAction.REMOVE);
            getSupportFragmentManager().beginTransaction().add(R.id.stableHorseFragmentContainer, horseFragment, "horse").commit();
        } else {
            horseFragment = (HorseFragment) getSupportFragmentManager().findFragmentByTag("horse");
            horseFragment.setButtonAction(HorseAdapter.ButtonAction.REMOVE);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        horses = (ArrayList<Horse>) savedInstanceState.getSerializable("horses");
        horseFragment.setHorses(horses);

        Horse selectedHorse = (Horse) savedInstanceState.getSerializable("selectedHorse");
        if(selectedHorse != null) {
            horseDetailFragment = new HorseDetailFragment();
            horseDetailFragment.setHorse(selectedHorse);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportFragmentManager().beginTransaction().replace(R.id.stableHorseDetailFragmentContainer, horseDetailFragment).commit();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("horses", horses);
        if(horseDetailFragment != null) {
            outState.putSerializable("selectedHorse", horseDetailFragment.getHorse());
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportFragmentManager().beginTransaction().remove(horseDetailFragment).commit();
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                horseDetailFragment.dismiss();
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onHorsePressed(Horse horse) {
        horseDetailFragment = new HorseDetailFragment();
        horseDetailFragment.setHorse(horse);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            horseDetailFragment.show(getSupportFragmentManager(), "horseDetailDialog");
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager().beginTransaction().replace(R.id.stableHorseDetailFragmentContainer, horseDetailFragment, "horseDetail").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionbarSearch :
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.actionbarStable :
                startActivity(new Intent(this, StableActivity.class));
                return true;
            case R.id.actionbarExit :
                finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
