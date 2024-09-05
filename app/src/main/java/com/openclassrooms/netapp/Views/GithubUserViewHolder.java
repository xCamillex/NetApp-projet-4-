package com.openclassrooms.netapp.Views;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.databinding.FragmentMainItemBinding;

import java.lang.ref.WeakReference;
/**
 * ViewHolder pour la gestion de l'affichage d'un élément utilisateur GitHub dans un RecyclerView.
 *
 * Cette classe lie les données d'un utilisateur GitHub à une vue spécifique dans l'interface utilisateur,
 * en utilisant le binding `FragmentMainItemBinding`. Elle gère également les interactions utilisateur
 * via un callback, notamment lorsqu'un bouton de suppression est cliqué.
 */
public class GithubUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //`binding`: Référence au binding généré pour le layout d'un élément de la liste, permettant un accès facile aux vues de l'interface.
    private final FragmentMainItemBinding binding;
    /**`callbackWeakRef`: Une référence faible à l'interface `Listener` pour gérer les interactions utilisateur,
     * ce qui aide à éviter les fuites de mémoire.
     */
    private WeakReference<GithubUserAdapter.Listener> callbackWeakRef;

    public GithubUserViewHolder(FragmentMainItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /** `updateWithGithubUser`: Met à jour les vues de l'interface utilisateur avec les informations de l'utilisateur GitHub,
     * comme le nom, l'URL du profil, et l'avatar, tout en utilisant Glide pour le chargement de l'image.
     */
    public void updateWithGithubUser(GithubUser githubUser, RequestManager glide, GithubUserAdapter.Listener callback) {
        binding.fragmentMainItemTitle.setText(githubUser.getLogin());
        binding.fragmentMainItemWebsite.setText(githubUser.getHtmlUrl());
        glide.load(githubUser.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.fragmentMainItemImage);
        this.callbackWeakRef = new WeakReference<>(callback);
    }

    /** `onClick`: Méthode appelée lorsqu'une vue est cliquée. Elle utilise le callback pour notifier
     * l'activité ou le fragment associé lorsqu'un bouton de suppression est cliqué.
     */
    @Override
    public void onClick(View view) {
        GithubUserAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }
}
