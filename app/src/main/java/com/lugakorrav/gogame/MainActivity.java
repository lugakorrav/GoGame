package com.lugakorrav.gogame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FilenameFilter;

public class MainActivity extends AppCompatActivity {

    private String[] mFileList;
    private String path = Environment.getExternalStorageDirectory().toString() + "/GoGame/";
    private File mPath = new File(Environment.getExternalStorageDirectory() + "/GoGame/");
    private String mChosenFile;
    private static final String FTYPE = ".sgf";

    private void loadFileList() {
        try {
            mPath.mkdirs();
        } catch (SecurityException e) {
            Log.e("log", "unable to write on the sd card " + e.toString());
        }
        if (mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(FTYPE) || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
        } else {
            mFileList = new String[0];
        }
    }

    protected Dialog onCreateDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose file");
        if (mFileList == null) {
            Log.e("log", "Showing file picker before loading the file list");
            dialog = builder.create();
            return dialog;
        }
        builder.setItems(mFileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mChosenFile = mFileList[which];
                DataSGF dataSGF = ParserSGF.getDataSGF(path + mChosenFile);
                GoGameApp app = (GoGameApp) getApplicationContext();
                app.setDataSGF(dataSGF);
                Intent intent = new Intent(MainActivity.this, WatchRecordActivity.class);
                startActivity(intent);
            }
        });

        dialog = builder.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("log", Environment.getExternalStorageDirectory().toString());
    }

    public void onNewGameClick(View view) {
        GoGameApp app = (GoGameApp) getApplicationContext();
        app.setGoGame(null);
        Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
        startActivity(intent);
    }

    public void onResumeClick(View view) {
        GoGameApp app = (GoGameApp) getApplicationContext();
        GoGame goGame = app.getGoGame();
        if (goGame != null) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("count", goGame.getField().getSize());
            startActivity(intent);
        }
    }

    public void onWatchRecordClick(View view) {
        loadFileList();
        onCreateDialog().show();
    }
}
