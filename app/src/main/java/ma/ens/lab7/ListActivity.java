package ma.ens.lab7;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ma.ens.lab7.adapter.StarAdapter;
import ma.ens.lab7.service.StarService;

public class ListActivity extends AppCompatActivity {

    private StarAdapter starAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Afficher le bouton retour
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rv = findViewById(R.id.recycle_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        starAdapter = new StarAdapter(this, StarService.getInstance().findAll());
        rv.setAdapter(starAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        // Personnaliser le hint
        searchView.setQueryHint("Rechercher une star...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            shareApp();
            return true;
        } else if (id == android.R.id.home) {
            // Gérer le clic sur le bouton retour
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareApp() {
        String shareText = "Découvrez l'application Stars ! " +
                "Une application incroyable pour explorer vos stars préférées. " +
                "Téléchargez-la maintenant !";

        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Partager l'application Stars")
                .setText(shareText)
                .startChooser();
    }
}