package com.one.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView dateTextView;
        private CheckBox solvedCheckBox;
        private Crime crime;

        public void bindCrime(Crime crime) {
            this.crime = crime;

            titleTextView.setText(this.crime.getTitle());
            dateTextView.setText(this.crime.getDate().toString());
            solvedCheckBox.setChecked(this.crime.isSolved());
        }

        public CrimeHolder(View view) {
            super(view);

            titleTextView = (TextView) view.findViewById(R.id.list_item_crime_title_text_view);
            dateTextView = (TextView) view.findViewById(R.id.list_item_crime_date_text_view);
            solvedCheckBox = (CheckBox) view.findViewById(R.id.list_item_crime_solved_check_box);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder crimeHolder, int position) {
            crimeHolder.bindCrime(crimes.get(position));
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }

    private RecyclerView recyclerView;
    private CrimeAdapter adapter;
    private boolean subtitleVisible;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        if (null == adapter) {
            adapter = new CrimeAdapter(crimeLab.getCrimes());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (subtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).add(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        int crimesCount = CrimeLab.get(getActivity()).getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimesCount);
        if (!subtitleVisible) {
            subtitle = null;
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
    }
}
