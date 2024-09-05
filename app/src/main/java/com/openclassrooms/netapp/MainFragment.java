package com.openclassrooms.netapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.Utils.GithubStreams;
import com.openclassrooms.netapp.Utils.ItemClickSupport;
import com.openclassrooms.netapp.Views.GithubUserAdapter;
import com.openclassrooms.netapp.databinding.FragmentMainBinding;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Fragment principal de l'application qui affiche une liste d'utilisateurs GitHub dans un RecyclerView.
 *
 * Ce fragment utilise la liaison de vue (View Binding) pour accéder aux éléments définis dans le layout
 * `fragment_main.xml`. Il gère la configuration du RecyclerView, les actions de clic sur les éléments,
 * et la mise à jour de l'interface utilisateur après avoir récupéré les données via une requête HTTP.
 */
public class MainFragment extends Fragment implements GithubUserAdapter.Listener {
    /** `GithubUserAdapter.Listener`: Interface permettant de gérer les clics sur les boutons de suppression des
     *   utilisateurs.
     */
    private FragmentMainBinding binding;
    private Disposable disposable;
    private List<GithubUser> githubUsers;
    private GithubUserAdapter adapter;

    public MainFragment() { }

    /**`onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)`:
     *   Méthode appelée pour créer la vue du fragment. Elle configure le RecyclerView, le SwipeRefreshLayout,
     *   et les actions de clic sur les éléments du RecyclerView. Elle lance également une requête HTTP pour
     *   récupérer les utilisateurs GitHub.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        configureRecyclerView();
        configureSwipeRefreshLayout();
        configureOnClickRecyclerView();
        executeHttpRequestWithRetrofit();

        return view;
    }

    /**`onDestroy()`: Méthode appelée lorsque le fragment est détruit. Elle se charge de libérer les ressources
     *   associées aux requêtes en cours (RxJava).
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    /** - `configureOnClickRecyclerView()`: Configure un écouteur de clic pour les éléments du RecyclerView,
     *   permettant d'ouvrir `DetailActivity` avec les détails de l'utilisateur sélectionné.
     */
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(binding.fragmentMainRecyclerView, R.layout.fragment_main_item)
                // - `ItemClickSupport`: Utilitaire pour ajouter des écouteurs de clics sur les éléments du RecyclerView.
                .setOnItemClickListener((recyclerView, position, v) -> {
                    GithubUser user = adapter.getUser(position);
                    if (user != null) {
                        // Créez un Intent pour lancer DetailActivity
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra("USERNAME", user.getLogin());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClickDeleteButton(int position) {
        GithubUser user = adapter.getUser(position);
        Toast.makeText(getContext(), "You are trying to delete user : " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    /**  - `configureRecyclerView()`: Configure le RecyclerView avec un adaptateur personnalisé (`GithubUserAdapter`)
     *   et définit le `LayoutManager` pour l'affichage en liste verticale.
     */
    private void configureRecyclerView() {
        githubUsers = new ArrayList<>();
        this.adapter = new GithubUserAdapter(this.githubUsers, Glide.with(this), this);
        binding.fragmentMainRecyclerView.setAdapter(adapter);
        binding.fragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

     /** - `configureSwipeRefreshLayout()`: Configure le SwipeRefreshLayout pour rafraîchir les données lorsqu'un
            *   utilisateur effectue un balayage vers le bas.
      */
    private void configureSwipeRefreshLayout() {
        binding.fragmentMainSwipeContainer.setOnRefreshListener(() -> executeHttpRequestWithRetrofit());
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    /** - `executeHttpRequestWithRetrofit()`: Effectue une requête HTTP pour récupérer la liste des utilisateurs
     *   GitHub suivis par un utilisateur spécifique en utilisant Retrofit et RxJava.
     */
    private void executeHttpRequestWithRetrofit() {
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                updateUI(users);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    /** - `disposeWhenDestroy()`: Libère les ressources associées aux abonnements RxJava lorsque le fragment
     *   est détruit.
     */
    private void disposeWhenDestroy() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    /** - `updateUI(List<GithubUser> users)`: Met à jour l'interface utilisateur avec les utilisateurs GitHub
     *   récupérés, met à jour l'adaptateur et arrête l'animation de rafraîchissement.
     */
    private void updateUI(List<GithubUser> users) {
        githubUsers.clear();
        githubUsers.addAll(users);
        adapter.notifyDataSetChanged();
        binding.fragmentMainSwipeContainer.setRefreshing(false);
    }
}
