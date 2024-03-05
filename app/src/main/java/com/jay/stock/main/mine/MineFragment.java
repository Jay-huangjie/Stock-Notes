package com.jay.stock.main.mine;

import com.jay.core.mvvm.BaseFragment;
import com.jay.stock.R;
import com.jay.stock.databinding.FragmentMineLayoutBinding;
import com.jay.stock.databinding.FragmentSelectedLayoutBinding;

/**
 * created by hj on 2024/3/5.
 */
public class MineFragment extends BaseFragment<FragmentMineLayoutBinding> {

    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    public void initData() {

    }
}
