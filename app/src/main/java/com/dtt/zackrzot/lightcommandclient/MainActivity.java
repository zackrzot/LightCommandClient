package com.dtt.zackrzot.lightcommandclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final View lastView = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Stopping lights...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                SocketManager.sendMessage("STOP", view.getContext());
            }
        });

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView;
            ListView listView;
            View view;
            String[] items;
            ArrayAdapter<String> arrayAdapter;

            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_config, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    final EditText ip_field = (EditText) rootView.findViewById(R.id.editText_ip);
                    final EditText socket_field = (EditText) rootView.findViewById(R.id.editText_port);


                    textView.setText("Configure Server  >>>");

                    Button button = (Button) rootView.findViewById(R.id.button_connect);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(SocketManager.connect(ip_field.getText().toString(), socket_field.getText().toString())) {
                                Toast toast = Toast.makeText(getActivity().getApplication(), "Connected, maybe.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else{
                                Toast toast=Toast.makeText(getActivity().getApplication() ,"Not Connected.",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            View view = getActivity().getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                    });

                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);

                    view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    textView.setText("<<<   Break   >>>");

                    listView = (ListView) rootView.findViewById(R.id.listView);
                    items = new String[] { "BREAK1","BREAK2","BREAK3" };
                    arrayAdapter = new ArrayAdapter<String>(getActivity().getApplication(), android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            String item = ((TextView)view).getText().toString();
                            SocketManager.sendMessage(item, getActivity());
                        }
                    });




                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);

                    view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    textView.setText("<<<   Final Shot   >>>");

                    listView = (ListView) rootView.findViewById(R.id.listView);
                    items = new String[] { "FINAL1","FINAL2","FINAL3" };
                    arrayAdapter = new ArrayAdapter<String>(getActivity().getApplication(), android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            String item = ((TextView)view).getText().toString();
                            SocketManager.sendMessage(item, getActivity());
                        }
                    });


                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);

                    view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    textView.setText("<<<   Extra");

                    listView = (ListView) rootView.findViewById(R.id.listView);
                    items = new String[] { "TRIPLE", "MILLIONAIRE", "XFILES", "TRANCE", "YOUSUCK", "SCRATCH", "FRONTR", "FRONTL", "MIDDLER", "MIDDLEL", "BACKR", "BACKL", "AIRHORN", "SUSPENSE", "USA", "(padding)" };
                    arrayAdapter = new ArrayAdapter<String>(getActivity().getApplication(), android.R.layout.simple_list_item_1, items);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            String item = ((TextView)view).getText().toString();
                            SocketManager.sendMessage(item, getActivity());
                        }
                    });



                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }
}
