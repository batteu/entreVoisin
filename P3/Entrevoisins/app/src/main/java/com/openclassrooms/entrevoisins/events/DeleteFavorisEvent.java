package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * * Created by Baptiste  on 14/01/2021.
 **/
public class DeleteFavorisEvent {
    /**
     * Neighbour to delete
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public DeleteFavorisEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
