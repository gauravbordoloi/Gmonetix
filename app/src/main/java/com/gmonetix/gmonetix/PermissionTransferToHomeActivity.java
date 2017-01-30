package com.gmonetix.gmonetix;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import com.gmonetix.gmonetix.helper.RuntimePermission;

public class PermissionTransferToHomeActivity extends RuntimePermission {

    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestAppPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK},R.string.permission,REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }
}
