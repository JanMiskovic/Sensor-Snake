package sk.ukf.snake;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CheckBox okrajeCheckBox;
    private TextView sensorTextView;
    private int pocetS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        okrajeCheckBox = findViewById(R.id.okrajeCheckBox);

        sensorTextView = findViewById(R.id.textView2);
        sensorTextView.setOnClickListener(v -> {
            pocetS = Math.min(pocetS + 1, 3);
            sensorTextView.setText(new String(new char[pocetS]).replace("\0", "S") + "ENSOR 🐍");
        });
    }

    public void classicButton(View v) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("gamemode", "classic");
        intent.putExtra("okraje", okrajeCheckBox.isChecked());
        startActivity(intent);
    }

    public void modernButton(View v) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("gamemode", "modern");
        intent.putExtra("okraje", okrajeCheckBox.isChecked());
        startActivity(intent);
    }
}