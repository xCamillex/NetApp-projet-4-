package com.openclassrooms.netapp.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.databinding.FragmentMainItemBinding;

import java.util.List;

/**
 * Adaptateur pour la gestion et l'affichage d'une liste d'utilisateurs GitHub dans un RecyclerView.
 *
 * Cet adaptateur est utilisé pour afficher les données des utilisateurs GitHub dans une liste (RecyclerView).
 * Il prend en charge la liaison des données (GithubUser) aux vues correspondantes (ViewHolder) dans chaque élément de la liste.
 */

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    // `Listener`: Une interface pour gérer les interactions, comme le clic sur le bouton de suppression d'un élément.
    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    //`callback`: Un objet implémentant l'interface `Listener` permettant de notifier l'activité ou le fragment lorsque le bouton de suppression est cliqué.
    private final Listener callback;
    // FOR DATA
    //`githubUsers`: Une liste d'utilisateurs GitHub à afficher dans le RecyclerView.
    private List<GithubUser> githubUsers;
    //`glide`: Un objet `RequestManager` de la bibliothèque Glide utilisé pour charger les images (avatars des utilisateurs).
    private RequestManager glide;

    // CONSTRUCTOR
    public GithubUserAdapter(List<GithubUser> githubUsers, RequestManager glide, Listener callback) {
        this.githubUsers = githubUsers;
        this.glide = glide;
        this.callback = callback;
    }

    //`onCreateViewHolder`: Crée et renvoie un ViewHolder qui gère l'affichage d'un utilisateur GitHub.
    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        FragmentMainItemBinding binding = FragmentMainItemBinding.inflate(inflater, parent, false);

        return new GithubUserViewHolder(binding);
    }

    //`onBindViewHolder`: Lie les données de l'utilisateur GitHub à l'élément du RecyclerView en position donnée.
    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder viewHolder, int position) {
        viewHolder.updateWithGithubUser(this.githubUsers.get(position), this.glide, this.callback);
    }

    //`getItemCount`: Retourne le nombre total d'utilisateurs dans la liste.
    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }

    //`getUser`: Renvoie un utilisateur spécifique en fonction de sa position dans la liste.
    public GithubUser getUser(int position) {
        return this.githubUsers.get(position);
    }
}
