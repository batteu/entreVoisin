package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavorisEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * * Created by Baptiste  on 13/01/2021.
 **/
public class FavoriteFragment extends Fragment
{
    private NeighbourApiService mApiService;
    private List<Neighbour> mFavorites;
    private RecyclerView mRecyclerView;

    /**
     * Create and return a new instance
     * @return @{@link FavoriteFragment}
     */
    public static FavoriteFragment newInstance() {

        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mApiService = DI.getNeighbourApiService();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }


    /**
     * Init the List of favorite neighbours
     */
    private void initList() {

        mFavorites = mApiService.getNeighbours();
        Iterator<Neighbour> iterator = mFavorites.iterator();
        ArrayList<Neighbour> mListFavorite = new ArrayList();
        while (iterator.hasNext()) {
            Neighbour neighbour = iterator.next();
            if (neighbour.getFavoris() == true)
                mListFavorite.add(neighbour);
            else
                mListFavorite.remove(neighbour);
        }
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(this.requireContext(), mListFavorite));
       //mFavorites = mApiService.getFavorites();
       //mAdapter = new MyNeighbourRecyclerViewAdapter(this, mFavorites);
       //mRecyclerView.setAdapter(this.mAdapter);
    }



    @Override
    public void onResume() {
        super.onResume();
        System.out.println("NeighbourFragment :: onResume()");
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("NeighbourFragment :: onStart()");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("NeighbourFragment :: onStop()");
        EventBus.getDefault().unregister(this);
    }
    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteFavoris(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

}
