package com.jay.stock.main.ai;

import com.jay.core.mvvm.BaseFragment;
import com.jay.stock.R;
import com.jay.stock.databinding.FragmentAiLayoutBinding;
import com.jay.stock.databinding.FragmentSelectedLayoutBinding;

/**
 * created by hj on 2024/3/5.
 */
public class AiFragment extends BaseFragment<FragmentAiLayoutBinding> {

    public static AiFragment getInstance() {
        return new AiFragment();
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_ai_layout;
    }

    @Override
    public void initData() {

    }
}
