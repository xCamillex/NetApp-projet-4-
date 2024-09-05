package com.openclassrooms.netapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.openclassrooms.netapp.databinding.ActivityDetailBinding;
/**
 * Activité affichant les détails d'un utilisateur GitHub.
 *
 * Cette activité utilise le View Binding pour accéder aux éléments définis dans le layout `activity_detail.xml`.
 * Elle configure la Toolbar avec un bouton "Up" permettant de revenir à l'activité précédente.
 *
 * - Utilisation de View Binding pour obtenir une référence à la Toolbar et configurer les éléments de l'interface.
 * - Affichage d'un fragment de détail à l'aide du nom d'utilisateur passé via l'intent.
 * - Gestion du bouton "Up" pour naviguer en arrière dans la pile d'activités.
 */
public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    @Override
    /**`onCreate(@Nullable Bundle savedInstanceState)`: Méthode appelée lors de la création de l'activité.
     * Elle initialise le View Binding, configure la Toolbar avec le bouton "Up", et ajoute un fragment
     * de détail si l'état sauvegardé est nul. Elle reçoit également le nom d'utilisateur depuis l'intent
     * et remplace le conteneur de fragments avec `DetailFragment` contenant les détails de l'utilisateur.
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialisez le View Binding
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Utilisez le binding pour obtenir une référence à la Toolbar
        setSupportActionBar(binding.toolbar);
        // Affiche le bouton "Up" dans la barre d'action
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Recevoir le nom d'utilisateur depuis l'intent
            String username = getIntent().getStringExtra("USERNAME");

            // Ajouter le fragment de détail
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, DetailFragment.newInstance(username))
                    .commit();
        }


    }

    /** - `onOptionsItemSelected(MenuItem item)`:
     *   Méthode appelée lorsqu'un élément du menu est sélectionné. Elle gère le bouton "Up" de la barre d'action en
     *   appelant `onBackPressed()` pour revenir à l'activité précédente.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Gérer le bouton "Up" pour revenir en arrière
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}