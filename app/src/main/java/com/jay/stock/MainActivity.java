package com.jay.stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jay.core.mvvm.BaseActivity;
import com.jay.stock.databinding.ActivityMainBinding;
import com.jay.stock.main.ai.AiFragment;
import com.jay.stock.main.logic.LogicFragment;
import com.jay.stock.main.mine.MineFragment;
import com.jay.stock.main.selected.SelectedFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private LogicFragment logicFragment;
    private SelectedFragment selectedFragment;
    private AiFragment aiFragment;
    private MineFragment mineFragment;

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initEvent() {
        initFragment();
    }


    private void initFragment() {
        showLogicFragment();
        mBinding.mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_logic) {
                    showLogicFragment();
                } else if (id == R.id.action_selected) {
                    showSelectFragment();
                } else if (id == R.id.action_ai) {
                    showAiFragment();
                } else if (id == R.id.action_mine) {
                    showMineFragment();
                }
                return true;
            }
        });
    }


    public void showLogicFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (logicFragment == null) {
            logicFragment = LogicFragment.getInstance();
            fragmentTransaction.add(R.id.flContainer, logicFragment, "logicFragment");
        }
        commitShowFragment(fragmentTransaction, logicFragment);
    }

    public void showSelectFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (selectedFragment == null) {
            selectedFragment = SelectedFragment.getInstance();
            fragmentTransaction.add(R.id.flContainer, selectedFragment, "selectedFragment");
        }
        commitShowFragment(fragmentTransaction, selectedFragment);
    }

    public void showAiFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (aiFragment == null) {
            aiFragment = AiFragment.getInstance();
            fragmentTransaction.add(R.id.flContainer, aiFragment, "aiFragment");
        }
        commitShowFragment(fragmentTransaction, aiFragment);
    }

    public void showMineFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (mineFragment == null) {
            mineFragment = MineFragment.getInstance();
            fragmentTransaction.add(R.id.flContainer, mineFragment, "mineFragment");
        }
        commitShowFragment(fragmentTransaction, mineFragment);
    }


    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction, logicFragment);
        hideFragment(fragmentTransaction, selectedFragment);
        hideFragment(fragmentTransaction, aiFragment);
        hideFragment(fragmentTransaction, mineFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }


}