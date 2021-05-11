package sk.ukf.snake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;

public class Game extends View implements SensorEventListener {

    private SensorManager sm;
    private Sensor acc;
    private Paint paint;
    private Snake snake;
    private Food food;
    private int score;
    private HashMap<Integer, Bitmap> images;
    private SensorEvent lastEvent;
    private static Point screen;
    private static int gridX;
    private static int gridY;

    public Game(Context context) {
        super(context);
        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_GAME);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screen = new Point();
        display.getSize(screen);
        gridX = (int)(screen.x / 15);
        gridY = (int)((screen.y - 30) / ((screen.y - 30) / gridX));

        paint = new Paint();
        snake = new Snake();
        spawnFood();

        loadImages();
    }

    public void onDraw(Canvas canvas) {
        // Vykreslenie tela
        paint.setColor(Color.GREEN);
        for(Snake.SnakePart part : snake.getSnake()) {
            canvas.drawRect(part.x, part.y, part.x+gridX, part.y+gridY, paint);
        }

        // Vykreslenie hlavy
        Snake.SnakePart head = snake.getSnake().get(snake.getSnake().size()-1);
        canvas.drawBitmap(images.get(snake.getDirection()), head.x, head.y, paint);

        // Vykreslenie jedla
        canvas.drawBitmap(images.get(9), food.getX(), food.getY(), paint);
    }

    public void update() {
        checkDirectionChange();
        if (!snake.move()) {
            sm.unregisterListener(this);
            GameActivity.getUpdateThread().interrupt();
        }
        snake.checkFoodCollision(food);
    }

    private void spawnFood() {
        food = new Food(50, 50);
    }

    private void checkDirectionChange() {
        float x = lastEvent.values[0];
        float y = lastEvent.values[1];

        // Changing the snakes direction based on tilting the phone
        // CLASSIC GameMode (4 directions)
        if (GameActivity.getGamemode().equals("classic")) {
            if (Math.abs(x) > Math.abs(y)) {
                if (x > 1 && snake.getDirection() != 1) snake.setDirection(3);
                else if (x < -1 && snake.getDirection() != 3) snake.setDirection(1);
            } else {
                if (y > 1 && snake.getDirection() != 4) snake.setDirection(2);
                else if (y < -1 && snake.getDirection() != 2) snake.setDirection(4);
            }
        }

        // Changing the snakes direction based on tilting the phone
        // MODERN GameMode (8 directions)
        else {
            if (x > 1) {
                if (y < -1 && snake.getDirection() != 6) snake.setDirection(8);
                else if (y > 1 && snake.getDirection() != 5) snake.setDirection(7);
                else if (snake.getDirection() != 1) snake.setDirection(3);
            }
            else if (x < -1) {
                if (y < -1 && snake.getDirection() != 7) snake.setDirection(5);
                else if (y > 1 && snake.getDirection() != 8) snake.setDirection(6);
                else if (snake.getDirection() != 3) snake.setDirection(1);
            }
            else if (y > 1 && snake.getDirection() != 4) snake.setDirection(2);
            else if (y < -1 && snake.getDirection() != 2) snake.setDirection(4);
        }
    }

    // When the phone is tilted, save the event values for later.
    // We don't need to change the snake's direction every time the phone is tilted,
    // only when the game is about to update.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            lastEvent = event;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    private void loadImages() {
        images = new HashMap<>();
        Bitmap headright = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headright)), gridX, gridY, false);
        Bitmap headdown = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headdown)), gridX, gridY, false);
        Bitmap headleft = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headleft)), gridX, gridY, false);
        Bitmap headup = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headup)), gridX, gridY, false);
        Bitmap headupright = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headupright)), gridX, gridY, false);
        Bitmap headdownright = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headdownright)), gridX, gridY, false);
        Bitmap headdownleft = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headdownleft)), gridX, gridY, false);
        Bitmap headupleft = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.headupleft)), gridX, gridY, false);
        Bitmap food =  Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(this.getResources(), R.raw.food)), gridX, gridY, false);
        images.put(1, headright);
        images.put(2, headdown);
        images.put(3, headleft);
        images.put(4, headup);
        images.put(5, headupright);
        images.put(6, headdownright);
        images.put(7, headdownleft);
        images.put(8, headupleft);
        images.put(9, food);
    }

    public static Point getScreen() { return screen; }
    public static int getGridX() { return gridX; }
    public static int getGridY() { return gridY; }
}
