package com.jay.stock.main.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jay.core.mvvm.BaseActivity;
import com.jay.stock.R;
import com.jay.stock.databinding.ActivityAddNoteBinding;

/**
 * created by hj on 2026/1/9.
 */
public class AddNoteActivity extends BaseActivity<ActivityAddNoteBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_add_note;
    }

    @Override
    public void initEvent() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
