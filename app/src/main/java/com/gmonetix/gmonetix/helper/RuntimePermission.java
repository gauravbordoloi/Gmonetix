package com.gmonetix.gmonetix.helper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

public abstract class RuntimePermission extends Activity{

    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mErrorString = new SparseIntArray();
    }

    public abstract void onPermissionGranted(int requestCode);

    public void requestAppPermission(final String[]requestedPermissions, final int stringId , final int requestCode){
        mErrorString.put(requestCode,stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermission = false;
        for(String permission: requestedPermissions){
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this,permission);
            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if(showRequestPermission){
                Snackbar.make(findViewById(android.R.id.content),stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(RuntimePermission.this,requestedPermissions,requestCode);
                    }
                }).show();
            } else{
                ActivityCompat.requestPermissions(this,requestedPermissions,requestCode);
            }
        } else{
            onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int permission: grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if((grantResults.length>0) && PackageManager.PERMISSION_GRANTED == permissionCheck){
            onPermissionGranted(requestCode);
        } else {
            //Display message wehn contain some dangerous permission not accpeted
            Snackbar.make(findViewById(android.R.id.content),mErrorString.get(requestCode), Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package:"+getPackageName()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
            }).show();
        }
    }
}
