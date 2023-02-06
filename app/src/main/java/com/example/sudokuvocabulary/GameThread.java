package com.example.sudokuvocabulary;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private final SurfaceHolder mSurfaceHolder;
    private final GameView mGameView;
    private boolean mRunning;
    public static Canvas mCanvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        mSurfaceHolder = surfaceHolder;
        mGameView = gameView;
    }

    public void setRunning (boolean isRunning) {
        mRunning = isRunning;
    }

    @Override
    public void run() {
        while(mRunning) {
            mCanvas = null;

            try {
                mCanvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    mGameView.update();
                    mGameView.draw(mCanvas);
                }
            } catch (Exception e) {} finally {
                if (mCanvas != null) {
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
