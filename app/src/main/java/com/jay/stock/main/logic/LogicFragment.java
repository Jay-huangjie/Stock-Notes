package com.jay.stock.main.logic;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mBinding.toolbar.inflateMenu(R.menu.menu_logic);
        mBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    AddNoteActivity.start(getContext());
                    return true;
                } else if (item.getItemId() == R.id.action_search) {
                    click_search();
                    return true;
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: 设置 Adapter
        
        mBinding.refreshLayout.setEnableLoadMore(true);
        mBinding.refreshLayout.setEnableRefresh(true);
    }


    public void click_search(){
        // TODO: 处理搜索逻辑
        Toast.makeText(getContext(), "点击搜索", Toast.LENGTH_SHORT).show();
    }
}
