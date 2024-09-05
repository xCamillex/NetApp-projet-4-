package com.openclassrooms.netapp.Utils;

import io.reactivex.Observable;
import retrofit2.Call;
/**
 * La méthode `makeObservable` convertit un appel HTTP synchrone (de type `Call<T>`) en un `Observable<T>` réactif.
 *
 * Cette méthode permet de faire en sorte qu'un appel réseau, initialement bloquant, puisse être utilisé de manière non bloquante
 * en le transformant en un observable de la bibliothèque RxJava.
 *
 * Le `Observable` émet la réponse du serveur lorsqu'elle est disponible. Si la réponse est `null`,
 * une erreur est émise pour signaler qu'il y a eu un problème avec le corps de la réponse.
 *
 * En cas d'exception lors de l'exécution de l'appel, l'observable émet également une erreur,
 * ce qui permet de gérer facilement les échecs réseau dans une chaîne réactive.
 */
public class RxUtils {

    public static <T> Observable<T> makeObservable(Call<T> call) {
        return Observable.create(emitter -> {
            try {
                T response = call.execute().body();
                if (response != null) {
                    emitter.onNext(response);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NullPointerException("Response body is null"));
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
