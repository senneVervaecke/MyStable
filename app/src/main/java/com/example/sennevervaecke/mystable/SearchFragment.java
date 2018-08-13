package com.example.sennevervaecke.mystable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;


public class SearchFragment extends Fragment {

    private SearchFragmentComm comm;
    private EditText searchBar;
    private ProgressBar progressBar;
    private String input = "";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setText(input);
        progressBar = view.findViewById(R.id.searchProgres);
        progressBar.setVisibility(View.GONE);
        view.findViewById(R.id.searchButton).setOnClickListener(new SearchButtonPressed());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentComm) {
            comm = (SearchFragmentComm) context;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("input", searchBar.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    public class SearchButtonPressed implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            comm.onSearchPressed(searchBar.getText().toString());
        }
    }

    public interface SearchFragmentComm {
        void onSearchPressed(String value);
    }
}
