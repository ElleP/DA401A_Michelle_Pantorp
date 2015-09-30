package com.example.elle.assignment_5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment {

    ArrayList<String> mQuote = new ArrayList<>();
    QouteAdapter mAdapter;
    ProgressBar mProgressbar;

    public QuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_quote, container, false);

        mProgressbar = (ProgressBar) v.findViewById(R.id.progress_bar);

        final ListView listview = (ListView) v.findViewById(R.id.quote_list);
        mAdapter = new QouteAdapter(mQuote, getActivity().getLayoutInflater());
        listview.setAdapter(mAdapter);

        listview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            List<String> selectedQuotes = new ArrayList<String>();

            public static final String TAG = "context_action_menu";

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                mode.setTitle(listview.getCheckedItemCount() + " selected");
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            private void deleteSelectedItems() {
                Log.i(TAG, "deleteSelectedItems");
                mQuote.removeAll(selectedQuotes);
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mAdapter.notifyDataSetChanged();
                selectedQuotes.clear();

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked) {
                    selectItem(position);
                } else {
                    deselectItem(position);
                }
                mode.invalidate();
            }

            void selectItem(int position) {
                selectedQuotes.add(mQuote.get(position));// + String.valueOf(position)
            }

            void deselectItem(int position) {
                selectedQuotes.remove(mQuote.get(position)); // + String.valueOf(position)
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.quote_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        URL url = null;
        try {
            url = new URL("https://api.github.com/zen?access_token=2a20bb2328fd26e1b502cebd042def03a08f0d7f");
            new DownloadQuote().execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onClick(View view) {

    }*/

    private class DownloadQuote extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            Log.i("doInBackground", "InonDoInBackground");
            URL url = params[0];

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    //Get inputStream
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    //Read inputStream
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String quote;
                    quote = bufferedReader.readLine();
                    Log.i("doInBackground", quote);
                    return quote;

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String quote) {
            mProgressbar.setVisibility(View.GONE);
            mQuote.add(quote);
            mAdapter.notifyDataSetChanged();
        }

    }


}




