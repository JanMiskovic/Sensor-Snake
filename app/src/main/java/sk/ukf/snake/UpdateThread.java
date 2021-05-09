package sk.ukf.snake;

import android.os.Handler;

public class UpdateThread extends Thread {

    private Handler handler;
    private static int gamespeed = 300;
    private static volatile boolean isRunning;

    public UpdateThread(Handler handler) {
        super();
        this.handler = handler;
        isRunning = true;
    }

    public void run() {
        while (isRunning) {
            try {
                this.sleep(Math.max(gamespeed, 80));
            } catch (Exception e) {}
            handler.sendEmptyMessage(0);
        }
    }

    public static int getGamespeed() { return gamespeed; }
    public static void setGamespeed(int gamespeed) { UpdateThread.gamespeed = gamespeed; }
    public static void setIsRunning(boolean b) { isRunning = b; }
}
