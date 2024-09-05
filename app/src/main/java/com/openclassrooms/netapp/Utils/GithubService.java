package com.openclassrooms.netapp.Utils;

import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.Models.GithubUserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

/**
 * Interface pour la communication avec l'API GitHub.
 * <p>
 * Cette interface définit les méthodes pour accéder aux différentes ressources
 * de l'API GitHub concernant les utilisateurs. Elle utilise Retrofit pour la
 * sérialisation et désérialisation des données JSON, ainsi que RxJava pour
 * la gestion des appels asynchrones.
 * </p>
 */

public interface GithubService {

    /**
     * Récupère la liste des utilisateurs que l'utilisateur spécifié suit.
     *
     * @param username Nom d'utilisateur GitHub pour lequel obtenir la liste des abonnements.
     * @return Un observable contenant une liste d'objets GithubUser.
     */
    @GET("users/{username}/following")
    Observable<List<GithubUser>> getFollowing(@Path("username") String username);

    /**
     * Récupère les informations détaillées sur l'utilisateur spécifié.
     *
     * @param username Nom d'utilisateur GitHub pour lequel obtenir les informations détaillées.
     * @return Un appel pour obtenir un objet GithubUserInfo.
     */
    @GET("/users/{username}")
    Call<GithubUserInfo> getUserInfos(@Path("username") String username);

    /**
     * Instance de Retrofit configurée pour interagir avec l'API GitHub.
     * <p>
     * Utilise l'URL de base de l'API GitHub, ainsi qu'un convertisseur Gson
     * pour la sérialisation et désérialisation des objets JSON, et un adaptateur
     * RxJava2 pour la gestion des appels asynchrones.
     * </p>
     */
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
