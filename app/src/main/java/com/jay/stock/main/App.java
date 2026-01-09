package com.jay.stock.main;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.jay.stock.main.db.LitePal;

/**
 * created by hj on 2026/1/9.
 */
public class App extends Application {
    public static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        LitePal.loadLitePalDb();
    }
}
