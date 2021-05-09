package sk.ukf.snake;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private Handler handler;
    private static UpdateThread updateThread;
    private static String gamemode;
    private static boolean okraje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        gamemode = intent.getStringExtra("gamemode");
        okraje = intent.getBooleanExtra("okraje", true);

        game = new Game(this);
        setContentView(game);

        createUpdateHandler();
        updateThread = new UpdateThread(handler);
        updateThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void createUpdateHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                game.update();
                game.invalidate();
                super.handleMessage(msg);
            }
        };
    }

    public static UpdateThread getUpdateThread() { return updateThread; }
    public static String getGamemode() { return gamemode; }
    public static boolean getOkraje() { return okraje; }
}