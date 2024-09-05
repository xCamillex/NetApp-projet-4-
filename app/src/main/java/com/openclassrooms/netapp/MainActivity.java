package com.openclassrooms.netapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.openclassrooms.netapp.databinding.ActivityMainBinding;
/**
 * Activité principale de l'application, qui gère l'affichage du fragment principal dans l'interface utilisateur.
 *
 * Cette activité utilise la liaison de vue (View Binding) pour accéder aux éléments de l'interface définis dans
 * le layout `activity_main.xml`. Elle configure et affiche le fragment principal `MainFragment` dans le conteneur
 * spécifié par `R.id.activity_main_frame_layout`.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainFragment mainFragment;

    /**`onCreate(Bundle savedInstanceState)`: Méthode appelée lors de la création de l'activité. Elle initialise
     *   le binding pour accéder facilement aux vues et configure le fragment principal en utilisant la méthode
     *   `configureAndShowMainFragment()`.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureAndShowMainFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    /**`configureAndShowMainFragment()`: Cette méthode vérifie si le fragment principal `MainFragment` est déjà
     *   attaché à l'activité. Si ce n'est pas le cas, elle crée une nouvelle instance du fragment et l'ajoute
     *   au conteneur de fragment spécifié par `R.id.activity_main_frame_layout`.
     */
    private void configureAndShowMainFragment() {
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }
}
