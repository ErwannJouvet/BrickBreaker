package com.example.myapplication.Class;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.myapplication.Interface.IGameListener;
import com.example.myapplication.Interface.IGameviewListener;
import com.example.myapplication.R;

import java.util.Random;

public class GameView extends View implements IGameListener, IGameviewListener {

    Context context;
    int dWidth, dHeight;

    // position
    float ballX, ballY;
    float paddleX, paddleY;
    float oldX, oldPaddleX;
    Velocity velocity = new Velocity(15, 17);
    // to update
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    // points, health, bricks
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();

    // game element
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;

    // drawable
    Bitmap ball, paddle;
    int ballWidth, ballHeight;

    Random random;

    // bricks
    Brick[] bricks = new Brick[30];
    int numBricks = 0;
    int brokenBricks = 0;

    boolean gameOver = false;
    boolean result;

    private IGameviewListener listener;
    public void setGameviewListener(IGameviewListener listener) {
        this.listener = listener;
    }

    private static GameView instance;

    public static GameView getInstance(Context context) {
        if(instance == null) {
            instance = new GameView(context);
        }
        return instance;
    }

    public static void deleteInstance() {
        instance = null;
    }

    // let's get started
    public GameView(Context context) {
        super(context);
        this.context = context;

        // Bitmaps
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);

        // Handler and run
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        // is something there ?
        // maybe ?

        // text
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        healthPaint.setColor(Color.GREEN);

        //brickPaint.setColor(Color.argb(255, 249, 129, 0));
        brickPaint.setColor(ContextCompat.getColor(context, R.color.brick));

        // calculate height and width of screen
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        random = new Random();

        // Ball
        ballX = random.nextInt(dWidth - 50);
        ballY = dHeight / 3;
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();

        // Paddle, y is for centering
        paddleY = (dHeight * 4) / 5;
        paddleX = dWidth / 2 - paddle.getWidth() / 2;

        // create Bricks
        createBricks();
    }

    private void createBricks() {
        int brickWidth = dWidth / 8;
        int brickHeight = dHeight / 16;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.fondVert));
        ballX += velocity.getX();
        ballY += velocity.getY();

        // if the ball touches the side, we reverse the velocity
        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        // same if it touches the top
        if (ballY <= 0) {
            velocity.setY(velocity.getY() * -1);
        }

        if (ballY > paddleY + paddle.getHeight()) {
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            ballY = dHeight / 3;
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0) {
                this.launchGameOver(false);
            }
        }
        if (
                (ballX + ball.getWidth()) >= paddleX
                && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ball.getHeight() >= paddleY)
                && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())
        ) {
            velocity.setX(velocity.getX() + 1);
            velocity.setY((velocity.getY() + 1) * -1);
        }

        canvas.drawBitmap(ball, ballX, ballY, null);
        canvas.drawBitmap(paddle, paddleX, paddleY, null);

        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].isVisible()) {
                canvas.drawRoundRect(
                    new RectF(
                        bricks[i].column * bricks[i].width + 1,
                        bricks[i].row * bricks[i].height + 1,
                        bricks[i].column * bricks[i].width + bricks[i].width - 1,
                        bricks[i].row * bricks[i].height + bricks[i].height - 1
                    ),
                 15,
                 15,
                    brickPaint
                );
            }
            canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
            if (life == 2) {
                healthPaint.setColor(Color.YELLOW);
            }
            if (life == 1) {
                healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * life, 80, healthPaint);

            for (int j = 0; j < numBricks; j++) {
                if (bricks[j].isVisible()) {
                    if (
                            ballX + ballWidth >= bricks[j].column * bricks[j].width
                            && ballX <= bricks[j].column * bricks[j].width + bricks[j].width
                            && ballY <= bricks[j].row * bricks[j].height + bricks[j].height
                            && ballY >= bricks[j].row * bricks[j].height
                    ) {
                        velocity.setY((velocity.getY() + 1) * -1);
                        bricks[j].setInvisible();
                        points += 10;
                        brokenBricks++;
                        if (brokenBricks == numBricks) {
                            this.launchGameOver(true);
                        }
                    }
                }
            }

            if (brokenBricks == numBricks) {
                gameOver = true;
            }

            if (!gameOver) {
                handler.postDelayed(runnable, UPDATE_MILLIS);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= paddleY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;
                if (newPaddleX <= 0)
                    paddleX = 0;
                else if (newPaddleX >= dWidth - paddle.getWidth())
                    paddleX = dWidth - paddle.getWidth();
                else
                    paddleX = newPaddleX;

            }
        }
        return true;
    }

    private void launchGameOver(boolean result) {
        handler.removeCallbacksAndMessages(null);
        if (listener != null) {
            listener.onGameviewOver(result, points);
        }
    }

    private int xVelocity() {
        int[] values = {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(6);
        return values[index];
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public void onPlayGame(boolean play) {

    }

    @Override
    public void onSelectLevel(int level) {

    }

    @Override
    public void onSelectionLevel(boolean selectionLevel) {

    }

    @Override
    public void onGameOver(boolean over, boolean result, int score) {

    }

    @Override
    public void onHome() {

    }

    @Override
    public void onGameviewOver(boolean result, int score) {

    }
}
