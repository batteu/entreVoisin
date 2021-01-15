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

    private int mPosition;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private Neighbour mNeighbourSelected;


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
    public FloatingActionButton AddFavoris;

    @BindView(R.id.adresse)
    public TextView mAdresse;

    @BindView(R.id.nomprofile)
    public TextView mNomProfile;

    @OnClick(R.id.buttonretour)
    public void mRetourButton() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        ButterKnife.bind(this);

        Intent profileIntent = getIntent();

        mPosition = profileIntent.getIntExtra("POSITION", 0);

        mNeighbourSelected = mApiService.getNeighbourPosition(mPosition);


        /** Affichage de la photo de Profile*/
        Glide.with(this).load(mNeighbourSelected.getAvatarUrl()).into(mImageProfile);

        mNomProfile.setText(mNeighbourSelected.getName());
        mCadreNomProfile.setText(mNeighbourSelected.getName());
        mAdresse.setText(mNeighbourSelected.getAddress());
        mNumeroTelephone.setText(mNeighbourSelected.getPhoneNumber());
        mDescription.setText(mNeighbourSelected.getAboutMe());


        /**Bouton ADDFavoris*/
        if (mNeighbourSelected.getFavoris())
            AddFavoris.setImageResource(R.drawable.ic_star_white_24dp);
        else
            AddFavoris.setImageResource(R.drawable.ic_star_border_white_24dp);

        AddFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNeighbourSelected.getFavoris()) {
                    Toast.makeText(Profile_Activity.this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
                    AddFavoris.setImageResource(R.drawable.ic_star_white_24dp);
                    mNeighbourSelected.setFavoris(true);
                } else {
                    Toast.makeText(Profile_Activity.this, "Supprimé des favoris", Toast.LENGTH_SHORT).show();
                    AddFavoris.setImageResource(R.drawable.ic_star_border_white_24dp);
                    mNeighbourSelected.setFavoris(false);
                }
            }
        });
    }
}
