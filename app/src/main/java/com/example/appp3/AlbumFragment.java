package com.example.appp3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appp3.adapter.MusicAdapter;
import com.example.appp3.adapter.SongRecyclerViewAdapter;

import static com.example.appp3.AllSongsActivity.musicFiles;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    RecyclerView recyclerView;
    SongRecyclerViewAdapter songRecyclerViewAdapter;
    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if(!(musicFiles.size()<1))
        {
            songRecyclerViewAdapter = new SongRecyclerViewAdapter(getContext(), musicFiles);
            recyclerView.setAdapter(songRecyclerViewAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        return view;
    }
}
