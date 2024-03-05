package com.jay.stock.main.logic;

import com.jay.core.mvvm.BaseFragment;
import com.jay.stock.R;
import com.jay.stock.databinding.FragmentLogicLayoutBinding;

/**
 * created by hj on 2024/3/5.
 */
public class LogicFragment extends BaseFragment<FragmentLogicLayoutBinding> {

    public static LogicFragment getInstance() {
        return new LogicFragment();
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_logic_layout;
    }

    @Override
    public void initData() {

    }


    public void click_search(){

    }
}
