package com.example.mytest;

import android.Manifest;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.PermissionChecker;

import com.byteteacher.library.quicklog.LogSaver;
import com.example.myquicklog.R;
import com.byteteacher.library.base.BaseActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "cj";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                Toast.makeText(MainActivity.this, ""+o, Toast.LENGTH_SHORT).show();
            }
        });

        launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:

                LogSaver.saveAppLog("存储日志","logsaveer");

                Hell.show();

                break;
        }
    }


}
