package com.openclassrooms.netapp.Utils;

import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * NetworkAsyncTask est une classe personnalisée dérivée d'AsyncTask pour gérer les opérations réseau sur un thread en arrière-plan.
 * Elle utilise une WeakReference pour éviter les fuites de mémoire en référant faiblement à l'interface Listeners,
 * permettant ainsi à l'objet de rappel (généralement une activité ou un fragment) d'être collecté par le garbage collector si nécessaire.
 *
 * L'AsyncTask suit le cycle de vie typique d'une tâche asynchrone avec les méthodes onPreExecute, doInBackground et onPostExecute.
 * - onPreExecute() est appelée avant le démarrage de la tâche en arrière-plan pour effectuer des préparations.
 * - doInBackground() exécute la tâche principale (ici, une requête HTTP) sur un thread séparé.
 * - onPostExecute() est appelée après la fin de la tâche pour mettre à jour l'interface utilisateur avec le résultat.
 *
 * Cette classe permet donc de déléguer facilement des opérations réseau sans bloquer le thread principal de l'application.
 */

public class NetworkAsyncTask extends android.os.AsyncTask<String, Void, String> {

    public interface Listeners {
        void onPreExecute();
        void doInBackground();
        void onPostExecute(String success);
    }

    private final WeakReference<Listeners> callback;

    public NetworkAsyncTask(Listeners callback){
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute();
        Log.e("TAG", "AsyncTask is started.");
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success);
        Log.e("TAG", "AsyncTask is finished.");
    }

    @Override
    protected String doInBackground(String... url) {
        this.callback.get().doInBackground();
        Log.e("TAG", "AsyncTask doing some big work...");
        return MyHttpURLConnection.startHttpRequest(url[0]);
    }
}