package com.openclassrooms.netapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.openclassrooms.netapp.Models.GithubUserInfo;
import com.openclassrooms.netapp.Utils.GithubService;
import com.openclassrooms.netapp.databinding.FragmentDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment affichant les détails d'un utilisateur GitHub.
 *
 * Ce fragment utilise View Binding pour lier les éléments de l'interface définis dans le layout
 * `fragment_detail.xml` et affiche les informations d'un utilisateur GitHub, telles que son avatar, son nom d'utilisateur,
 * le nombre de suivis, de followers et de dépôts publics.
 *
 * - Utilisation de View Binding pour accéder aux éléments de l'interface et les mettre à jour.
 * - Utilisation de Retrofit pour effectuer des requêtes réseau pour obtenir les informations de l'utilisateur GitHub.
 * - Gestion des erreurs de réseau et de réponse pour informer l'utilisateur en cas de problème.
 */
public class DetailFragment extends Fragment {

    private static final String ARG_USERNAME = "username";

    private FragmentDetailBinding binding;

    /** - `newInstance(String username)`:
     *   Méthode statique permettant de créer une nouvelle instance du fragment avec le nom d'utilisateur passé en argument.
     *   Elle prépare un `Bundle` contenant le nom d'utilisateur, qui sera utilisé pour effectuer la requête réseau.
     */
    public static DetailFragment newInstance(String username) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    /**`onCreate(@Nullable Bundle savedInstanceState)`:
     *   Méthode appelée lors de la création du fragment. Elle récupère le nom d'utilisateur à partir des arguments du fragment,
     *   mais ne fait rien avec cette information à ce stade. La requête réseau est effectuée dans `onViewCreated`.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String username = getArguments().getString(ARG_USERNAME);
            // Utilisez le nom d'utilisateur pour effectuer une requête réseau et afficher les détails
        }
    }

    /**`onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)`:
     *   Méthode appelée pour créer la vue du fragment. Elle utilise View Binding pour lier les éléments de l'interface et
     *   renvoie la vue racine du fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**`onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)`:
     *   Méthode appelée une fois que la vue du fragment a été créée. Elle récupère le nom d'utilisateur depuis les arguments
     *   et appelle `fetchUserDetails` pour obtenir les détails de l'utilisateur depuis l'API GitHub.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String username = getArguments().getString(ARG_USERNAME);
            fetchUserDetails(username);
        }
    }

    /**`fetchUserDetails(String username)`:
     *   Méthode pour effectuer une requête réseau à l'aide de Retrofit pour obtenir les informations de l'utilisateur GitHub.
     *   Elle crée une instance de Retrofit, configure le service API, et envoie la requête pour récupérer les informations
     *   de l'utilisateur. Les résultats sont traités dans les méthodes `onResponse` et `onFailure`.
     */
    private void fetchUserDetails(String username) {
        // Utiliser votre propre instance de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService service = retrofit.create(GithubService.class);
        Call<GithubUserInfo> call = service.getUserInfos(username);

        call.enqueue(new Callback<GithubUserInfo>() {
            @Override
            public void onResponse(Call<GithubUserInfo> call, Response<GithubUserInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GithubUserInfo user = response.body();
                    updateUI(user);  // Met à jour l'UI avec les informations de l'utilisateur
                } else {
                    // Gérez les erreurs de réponse ici
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GithubUserInfo> call, Throwable t) {
                // Gérez les erreurs de réseau ici
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**`updateUI(GithubUserInfo user)`:
     *   Méthode pour mettre à jour l'interface utilisateur avec les informations récupérées de l'utilisateur GitHub. Elle utilise
     *   Glide pour charger l'image de l'utilisateur et mettre à jour les TextViews avec les détails de l'utilisateur.
     */
    private void updateUI(GithubUserInfo user) {
        Glide.with(this)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(binding.avatarImageView);
        binding.usernameTextView.setText(user.getLogin()
        );
        binding.followingTextView.setText(String.valueOf(user.getFollowing()));
        binding.followersTextView.setText(String.valueOf(user.getFollowers()));
        binding.publicReposTextView.setText(String.valueOf(user.getPublicRepos()));
        binding.TvFollowing.setText("Following: " );
        binding.TvFollowers.setText("Followers: " );
        binding.TvPublicRepos.setText("Public Repos: " );
    }
}
