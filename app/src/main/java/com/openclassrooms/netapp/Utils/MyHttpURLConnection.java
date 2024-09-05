package com.openclassrooms.netapp.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Cette classe fournit une méthode pour effectuer une requête HTTP GET simple
 * et récupérer la réponse sous forme de chaîne de caractères.
 */

public class MyHttpURLConnection {

    /**
     * Effectue une requête HTTP GET à l'URL spécifiée et retourne la réponse en tant que chaîne.
     *
     * @param urlString L'URL de la requête HTTP sous forme de chaîne de caractères.
     * @return La réponse de la requête HTTP en tant que chaîne de caractères. En cas d'erreur, une chaîne vide est retournée.
     */
    public static String startHttpRequest(String urlString){

        StringBuilder stringBuilder = new StringBuilder();

        try {
            // 1. Déclare une connexion URL
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 2. Ouvre un InputStream pour la connexion
            conn.connect();
            InputStream in = conn.getInputStream();
            // 3. Télécharge et décode la réponse sous forme de chaîne en utilisant StringBuilder
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (MalformedURLException exception){
            // Cette exception est levée si l'URL fournie est mal formée.
            // Ici, on pourrait journaliser l'erreur ou gérer l'exception de manière appropriée.
            exception.printStackTrace();

        } catch (IOException exception) {
            // Cette exception est levée en cas d'erreur d'entrée/sortie lors de la connexion ou de la lecture des données.
            // Ici, on pourrait journaliser l'erreur ou gérer l'exception de manière appropriée.
            exception.printStackTrace();

        } catch (Exception e){
            // Cette exception est levée pour toute autre erreur inattendue.
            // Ici, on pourrait journaliser l'erreur ou gérer l'exception de manière appropriée.
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

}