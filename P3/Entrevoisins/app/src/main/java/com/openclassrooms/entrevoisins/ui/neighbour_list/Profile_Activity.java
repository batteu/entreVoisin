package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Profile_Activity extends AppCompatActivity {


    private NeighbourApiService mApiService;



    /**
     * Les boutons de la page Profile
     */
    @BindView(R.id.imageProfile)
    public ImageView mImageProfile;

    @BindView(R.id.cadrenomprofile)
    public TextView mCadreNomProfile;

    @BindView(R.id.numerotelephone)
    public TextView mNumeroTelephone;

    @BindView(R.id.description)
    public TextView mDescription;

    @BindView(R.id.buttonFavoris)
    public FloatingActionButton mButtonFavoris;

    @BindView(R.id.adresse)
    public TextView mAdresse;

    @BindView(R.id.nomprofile)
    public TextView mNomProfile;

    @OnClick(R.id.buttonretour)
    public void mRetourButton() {
        finish();
    }

    @BindView(R.id.facebook)
    public TextView mFacebook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();
        displayProfile();
        actionFavorisButton();
    }

    private  void displayProfile(){
        if(getIntent().hasExtra("Neighbour")){
            Neighbour neighbour = getIntent().getExtras().getParcelable("Neighbour");
            Glide.with(this)
                    .load(neighbour.getAvatarUrl())
                    .into(mImageProfile);

            mNomProfile.setText(neighbour.getName());
            mCadreNomProfile.setText(neighbour.getName());
            mAdresse.setText(neighbour.getAddress());
            mNumeroTelephone.setText(neighbour.getPhoneNumber());
            mDescription.setText(neighbour.getAboutMe());
            mFacebook.setText("www.facebook.fr/" + neighbour.getName());
        }
    }

    private void actionFavorisButton(){
        if(getIntent().hasExtra("Neighbour")){
            Neighbour neighbour = getIntent().getParcelableExtra("Neighbour");

            if(mApiService.getFavorites().contains(neighbour)){
                mButtonFavoris.setImageResource(R.drawable.ic_star_white_24dp);
            }
            mButtonFavoris.setOnClickListener(v -> {
                if(!mApiService.getFavorites().contains(neighbour)) {
                    mApiService.addToFavorite(neighbour);
                    mButtonFavoris.setImageResource(R.drawable.ic_star_white_24dp);
                    Toast.makeText(this, "ajouté aux Favoris", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"Déja Favoris", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
