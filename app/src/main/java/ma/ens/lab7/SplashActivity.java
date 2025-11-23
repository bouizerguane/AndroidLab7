package ma.ens.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private static final long SPLASH_DURATION = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);

        // Lancer l'animation
        logo.animate()
                .rotation(360f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .translationYBy(1000f)
                .alpha(0f)
                .setDuration(SPLASH_DURATION)
                .start();

        // Redirection après l'animation
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, ListActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}