package com.openclassrooms.netapp.Utils;

import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.Models.GithubUserInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * Classe utilitaire pour gérer les flux de données liés à l'API GitHub en utilisant RxJava.
 * <p>
 * Cette classe fournit des méthodes pour interagir avec les données utilisateur de l'API GitHub
 * et utilise RxJava pour gérer les appels asynchrones et les flux de données.
 * </p>
 */

public class GithubStreams {

    /**
     * Récupère la liste des utilisateurs que l'utilisateur spécifié suit.
     * <p>
     * Utilise RxJava pour exécuter l'appel réseau de manière asynchrone et sur le thread de fond,
     * puis observe les résultats sur le thread principal. Le flux est configuré avec un timeout
     * de 10 secondes.
     * </p>
     *
     * @param username Nom d'utilisateur GitHub pour lequel obtenir la liste des abonnements.
     * @return Un observable contenant une liste d'objets GithubUser.
     */
    public static Observable<List<GithubUser>> streamFetchUserFollowing(String username) {
        GithubService gitHubService = GithubService.retrofit.create(GithubService.class);
        return gitHubService.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    /**
     * Récupère les informations détaillées sur l'utilisateur spécifié.
     * <p>
     * Utilise RxJava pour convertir l'appel réseau en un observable, exécute l'appel sur le thread
     * de fond et observe les résultats sur le thread principal. Le flux est configuré avec un timeout
     * de 10 secondes.
     * </p>
     *
     * @param username Nom d'utilisateur GitHub pour lequel obtenir les informations détaillées.
     * @return Un observable contenant un objet GithubUserInfo.
     */
    public static Observable<GithubUserInfo> streamFetchUserInfos(String username) {
        GithubService gitHubService = GithubService.retrofit.create(GithubService.class);
        Call<GithubUserInfo> call = gitHubService.getUserInfos(username);
        return RxUtils.makeObservable(call)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
    /**
     * Récupère la liste des utilisateurs que l'utilisateur spécifié suit et retourne les informations
     * détaillées sur le premier utilisateur de cette liste.
     * <p>
     * Cette méthode enchaîne plusieurs opérations :
     * 1. Récupère tous les utilisateurs que l'utilisateur spécifié suit.
     * 2. Sélectionne le premier utilisateur de la liste.
     * 3. Récupère les informations détaillées pour cet utilisateur.
     * </p>
     *
     * @param username Nom d'utilisateur GitHub pour lequel obtenir la liste des abonnements et des informations utilisateur.
     * @return Un observable contenant un objet GithubUserInfo pour le premier utilisateur de la liste.
     */
    public static Observable<GithubUserInfo> streamFetchUserFollowingAndFetchFirstUserInfos(String username) {
        return streamFetchUserFollowing(username) // 1 - Récupère tous les utilisateurs que l'utilisateur suit
                .map(users -> users.get(0)) // 2 - Sélectionne le premier utilisateur de la liste
                .flatMap(user -> streamFetchUserInfos(user.getLogin())); // 3 - Récupère les informations détaillées pour cet utilisateur
    }
}