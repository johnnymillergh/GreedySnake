package com.jm.snakepanelview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangman on 2018/1/8 14:43
 * Modified by Johnny Miller on 2018/4/16 12:53
 */

public class SnakePanelView extends View {
    private final static String TAG = SnakePanelView.class.getSimpleName();

    private List<List<GridSquare>> mGridSquare = new ArrayList<>();
    private List<GridPosition> mSnakePositions = new ArrayList<>();

    private GridPosition mSnakeHeader;//蛇头部位置
    private GridPosition mFoodPosition;//食物的位置
    private List<GridPosition> foodPositions = new ArrayList<>();//食物的位置
    private int snakeLength = 4;
    private long speed = 4;
    private int snakeDirection = GameType.RIGHT;
    private boolean isEndGame = false;
    private int gridSize = 20;
    private Paint gridPaint = new Paint();
    private Paint strokePaint = new Paint();
    private int rectSize = dp2px(getContext(), 15);
    private int startX, startY;

    public SnakePanelView(Context context) {
        this(context, null);
    }

    public SnakePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        List<GridSquare> squares;
        for (int i = 0; i < gridSize; i++) {
            squares = new ArrayList<>();
            for (int j = 0; j < gridSize; j++) {
                squares.add(new GridSquare(GameType.GRID));
            }
            mGridSquare.add(squares);
        }
        mSnakeHeader = new GridPosition(10, 10);
        mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
        Random random = new Random();
        mFoodPosition = new GridPosition(random.nextInt(gridSize - 1), random.nextInt(gridSize - 1));

        for (int i = 0; i < random.nextInt(6); i++) {
            GridPosition gridPosition = new GridPosition(random.nextInt(gridSize - 1), random.nextInt(gridSize - 1));
            foodPositions.add(gridPosition);
        }
        isEndGame = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = w / 2 - gridSize * rectSize / 2;
        startY = dp2px(getContext(), 40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = startY * 2 + gridSize * rectSize;
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        //格子画笔
        gridPaint.reset();
        gridPaint.setAntiAlias(true);
        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setAntiAlias(true);

        strokePaint.reset();
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int left = startX + i * rectSize;
                int top = startY + j * rectSize;
                int right = left + rectSize;
                int bottom = top + rectSize;
                canvas.drawRect(left, top, right, bottom, strokePaint);
                gridPaint.setColor(mGridSquare.get(i).get(j).getColor());
                canvas.drawRect(left, top, right, bottom, gridPaint);
            }
        }
    }

    private void refreshFood(GridPosition foodPosition) {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(6); i++) {
            GridPosition randomGridPosition = new GridPosition(random.nextInt(gridSize - 1), random.nextInt
                    (gridSize - 1));
            mGridSquare.get(randomGridPosition.getX()).get(randomGridPosition.getY()).setType(GameType.FOOD);
        }
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setSnakeDirection(int snakeDirection) {
        if (this.snakeDirection == GameType.RIGHT && snakeDirection == GameType.LEFT) return;
        if (this.snakeDirection == GameType.LEFT && snakeDirection == GameType.RIGHT) return;
        if (this.snakeDirection == GameType.TOP && snakeDirection == GameType.BOTTOM) return;
        if (this.snakeDirection == GameType.BOTTOM && snakeDirection == GameType.TOP) return;
        this.snakeDirection = snakeDirection;
    }

    private class GameMainThread extends Thread {

        @Override
        public void run() {
            while (!isEndGame) {
                moveSnake(snakeDirection);
                checkCollision();
                refreshGridSquare();
                handleSnakeTail();
                postInvalidate();//重绘界面
                handleSpeed();
            }
        }

        private void handleSpeed() {
            try {
                sleep(1000 / speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //检测碰撞
    private void checkCollision() {
        //检测是否咬到自己
        GridPosition headPosition = mSnakePositions.get(mSnakePositions.size() - 1);
        for (int i = 0; i < mSnakePositions.size() - 2; i++) {
            GridPosition position = mSnakePositions.get(i);
            if (headPosition.getX() == position.getX() && headPosition.getY() == position.getY()) {
                //咬到自己 停止游戏
                isEndGame = true;
                showMessageDialog();
                return;
            }
        }

//        //判断是否吃到食物
//        if (headPosition.getX() == mFoodPosition.getX()
//                && headPosition.getY() == mFoodPosition.getY()) {
//            snakeLength++;
//            generateFood();
//        }

        // Check if snake have eaten the food
        for (int i = 0; i < foodPositions.size(); i++) {
            if (headPosition.equals(foodPositions.get(i))) {
                snakeLength++;
                foodPositions.remove(foodPositions.get(i));
                generateFood();
            }
        }
    }

    private void showMessageDialog() {
        post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getContext()).setMessage("Game Over!")
                        .setCancelable(false)
                        .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                reStartGame();
                            }
                        })
                        .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    public void reStartGame() {
        if (!isEndGame) return;
        for (List<GridSquare> squares : mGridSquare) {
            for (GridSquare square : squares) {
                square.setType(GameType.GRID);
            }
        }
        if (mSnakeHeader != null) {
            mSnakeHeader.setX(10);
            mSnakeHeader.setY(10);
        } else {
            mSnakeHeader = new GridPosition(10, 10);//蛇的初始位置
        }
        mSnakePositions.clear();
        mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
        snakeLength = 3;//蛇的长度
        snakeDirection = GameType.RIGHT;
        speed = 4;//速度

//        if (mFoodPosition != null) {
//            Random random = new Random();
//            mFoodPosition.setX(random.nextInt(gridSize - 1));
//            mFoodPosition.setY(random.nextInt(gridSize - 1));
//        } else {
//            mFoodPosition = new GridPosition(0, 0);
//        }
//        refreshFood(mFoodPosition);

        foodPositions.clear();
        generateFood();

        isEndGame = false;
        GameMainThread thread = new GameMainThread();
        thread.start();
    }

    //生成food
    private void generateFood() {
        Random random = new Random();
//        int foodX = random.nextInt(gridSize - 1);
//        int foodY = random.nextInt(gridSize - 1);
//        for (int i = 0; i < mSnakePositions.size() - 1; i++) {
//            if (foodX == mSnakePositions.get(i).getX() && foodY == mSnakePositions.get(i).getY()) {
//                //不能生成在蛇身上
//                foodX = random.nextInt(gridSize - 1);
//                foodY = random.nextInt(gridSize - 1);
//                //重新循环
//                i = 0;
//            }
//        }
//        mFoodPosition.setX(foodX);
//        mFoodPosition.setY(foodY);
//        refreshFood(mFoodPosition);

        if (foodPositions.size() == 0) {
            int count = random.nextInt(6) + 1;
            for (int i = 0; i < count; i++) {
                GridPosition gridPosition = new GridPosition(random.nextInt(gridSize - 1), random.nextInt(gridSize
                        - 1));
                foodPositions.add(gridPosition);
            }
            refreshFood();
        }
    }

    private void refreshFood() {
        for (GridPosition gp : foodPositions) {
            mGridSquare.get(gp.getX()).get(gp.getY()).setType(GameType.FOOD);
        }
    }

    private void moveSnake(int snakeDirection) {
        switch (snakeDirection) {
            case GameType.LEFT:
                if (mSnakeHeader.getX() - 1 < 0) {//边界判断：如果到了最左边 让他穿过屏幕到最右边
                    isEndGame = true;
                    showMessageDialog();
                    return;
                } else {
                    mSnakeHeader.setX(mSnakeHeader.getX() - 1);
                }
                mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
                break;
            case GameType.TOP:
                if (mSnakeHeader.getY() - 1 < 0) {
                    isEndGame = true;
                    showMessageDialog();
                    return;
                } else {
                    mSnakeHeader.setY(mSnakeHeader.getY() - 1);
                }
                mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
                break;
            case GameType.RIGHT:
                if (mSnakeHeader.getX() + 1 >= gridSize) {
                    isEndGame = true;
                    showMessageDialog();
                    return;
                } else {
                    mSnakeHeader.setX(mSnakeHeader.getX() + 1);
                }
                mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
                break;
            case GameType.BOTTOM:
                if (mSnakeHeader.getY() + 1 >= gridSize) {
                    isEndGame = true;
                    showMessageDialog();
                    return;
                } else {
                    mSnakeHeader.setY(mSnakeHeader.getY() + 1);
                }
                mSnakePositions.add(new GridPosition(mSnakeHeader.getX(), mSnakeHeader.getY()));
                break;
        }
    }

    private void refreshGridSquare() {
        for (GridPosition position : mSnakePositions) {
            mGridSquare.get(position.getX()).get(position.getY()).setType(GameType.SNAKE);
        }
    }

    private void handleSnakeTail() {
        int snakeLengthLocal = snakeLength;
        for (int i = mSnakePositions.size() - 1; i >= 0; i--) {
            if (snakeLengthLocal > 0) {
                snakeLengthLocal--;
            } else {//将超过长度的格子 置为 GameType.GRID
                GridPosition position = mSnakePositions.get(i);
                mGridSquare.get(position.getX()).get(position.getY()).setType(GameType.GRID);
            }
        }
        snakeLengthLocal = snakeLength;
        for (int i = mSnakePositions.size() - 1; i >= 0; i--) {
            if (snakeLengthLocal > 0) {
                snakeLengthLocal--;
            } else {
                mSnakePositions.remove(i);
            }
        }
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }
}
