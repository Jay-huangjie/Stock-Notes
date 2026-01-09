package com.jay.stock.main.componse;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * create by hj on 2020/7/16
 **/
public class AutoRefreshLayout extends SmartRefreshLayout {

    public AutoRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public AutoRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mReboundDuration = 200;
        setRefreshHeader(new MaterialHeader(context));
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "已经没有更多了哦";
        setRefreshFooter(new ClassicsFooter(context));
    }
}
