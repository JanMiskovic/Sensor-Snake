package sk.ukf.snake;

import android.os.Handler;

public class UpdateThread extends Thread {

    private Handler handler;
    private static int gamespeed = 300;

    public UpdateThread(Handler handler) {
        super();
        this.handler = handler;
    }

    public void run() {
        while (true) {
            try {
                this.sleep(Math.max(gamespeed, 80));
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            } catch (Exception e) {}
            handler.sendEmptyMessage(0);
        }
    }

    public static int getGamespeed() { return gamespeed; }
    public static void setGamespeed(int gamespeed) { UpdateThread.gamespeed = gamespeed; }
}
