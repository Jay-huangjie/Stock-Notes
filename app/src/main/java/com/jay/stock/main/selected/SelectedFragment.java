package com.jay.stock.main.selected;

import com.jay.core.mvvm.BaseFragment;
import com.jay.stock.R;
import com.jay.stock.databinding.FragmentLogicLayoutBinding;
import com.jay.stock.databinding.FragmentSelectedLayoutBinding;

/**
 * created by hj on 2024/3/5.
 */
public class SelectedFragment extends BaseFragment<FragmentSelectedLayoutBinding> {

    public static SelectedFragment getInstance() {
        return new SelectedFragment();
    }


    @Override
    public int getRootViewId() {
        return R.layout.fragment_selected_layout;
    }

    @Override
    public void initData() {

    }
}
