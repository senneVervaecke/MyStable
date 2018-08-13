package com.example.sennevervaecke.mystable;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchFragment.SearchFragmentComm, HorseFragment.HorseComm {

    private ArrayList<Horse> horses;
    private FindHorses findHorses;
    private HorseFragment horseFragment;
    private SearchFragment searchFragment;
    private HorseDetailFragment horseDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState == null) {
            horseFragment = new HorseFragment();
            searchFragment = new SearchFragment();
            horses = new ArrayList<Horse>();
            horseFragment.setHorses(horses);
            getSupportFragmentManager().beginTransaction().add(R.id.searchFragmentContainer, searchFragment, "search")
                    .add(R.id.horseListFragmentContainer, horseFragment, "horse").commit();
        } else {
            searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("search");
            horseFragment = (HorseFragment) getSupportFragmentManager().findFragmentByTag("horse");
        }
    }

    @Override
    public void onSearchPressed(String value) {
        findHorses = new FindHorses(new FindHorseHandler(), getApplicationContext());
        findHorses.execute(value);
        searchFragment.showProgress();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        horses = (ArrayList<Horse>) savedInstanceState.getSerializable("horses");
        horseFragment.setHorses(horses);

        Horse selectedHorse = (Horse) savedInstanceState.getSerializable("selectedHorse");
        if(selectedHorse != null) {
            horseDetailFragment = new HorseDetailFragment();
            horseDetailFragment.setHorse(selectedHorse);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportFragmentManager().beginTransaction().replace(R.id.horseDetailFragmentContainer, horseDetailFragment).commit();
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

    private void showMessage(String message){
        Snackbar.make(findViewById(R.id.searchActivityContainer), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onHorsePressed(Horse horse) {
        horseDetailFragment = new HorseDetailFragment();
        horseDetailFragment.setHorse(horse);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            horseDetailFragment.show(getSupportFragmentManager(), "horseDetailDialog");
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager().beginTransaction().replace(R.id.horseDetailFragmentContainer, horseDetailFragment, "horseDetail").commit();
        }
    }

    private class FindHorseHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg != null){
                searchFragment.hideProgress();
                switch (msg.what){
                    case FindHorses.RESPONSE :
                        horses = (ArrayList<Horse>) msg.obj;
                        horseFragment.setHorses(horses);
                        break;
                    case FindHorses.NORESULT :
                        horses = new ArrayList<Horse>();
                        horseFragment.setHorses(horses);
                        showMessage(getString(R.string.noResultMessage));
                        break;
                    case FindHorses.TOSHORTINPUT:
                        showMessage(getString(R.string.inputToShortMessage));
                        break;
                    case FindHorses.TOMANYRESULT:
                        showMessage(getString(R.string.toManyResultMessage));
                        break;
                    case FindHorses.NOINTERNET:
                        showMessage(getString(R.string.noInternetMessage));
                        break;
                    case FindHorses.CONNECTIONERROR:
                        showMessage(getString(R.string.connectionErrorMessage));
                        break;
                }
            }
        }
    }
}
