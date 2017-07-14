package com.devix.www.video_record_trimmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnK4LVideoListener;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;


public class TrimmerActivityx extends AppCompatActivity implements OnTrimVideoListener, OnK4LVideoListener {

    private static final String TAG = TrimmerActivityx.class.getSimpleName();
    private ProgressDialog mProgressDialog;
    private K4LVideoTrimmer mVideoTrimmer;
    private String videoLenovo = "/storage/sdcard0/dcim/VID_20170712_181135.mp4";
    private String videoSamsung = "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20170612-WA0019.mp4";
    private int intMaxDuration = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);
        Intent extraIntent = getIntent();
        String path = "";

        if (extraIntent != null) {
            path = extraIntent.getStringExtra(Utilsx.EXTRA_VIDEO_PATH);
        }

        if (extraIntent.getIntExtra(Utilsx.MAX_DURATION, 0) != 0) {
            intMaxDuration = extraIntent.getIntExtra(Utilsx.MAX_DURATION, 0);
        }

        //setting progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.trimming_progress));

        mVideoTrimmer = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(intMaxDuration);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnK4LVideoListener(this);
            //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
            mVideoTrimmer.setVideoURI(Uri.parse(path));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri uri) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TrimmerActivityx.this, getString(R.string.video_saved_at, uri.getPath()), Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent();
        intent.putExtra(Utilsx.REPORT, "" + uri);
        intent.putExtra(Utilsx.REPORT_CODE, Utilsx.REPORT_CODE_SUCCESS);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void cancelAction() {
        Log.i("log", "the action is cancelled.");
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finishWithError("the action is cancelled.", Utilsx.REPORT_CODE_TRIMMING_CANCELLED);
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("log", "error occured during trimming the video.");
                finishWithError("error occured during trimming the video.", Utilsx.REPORT_CODE_ERROR_DURING_TRIMMING_VIDEO);
            }
        });
    }

    @Override
    public void onVideoPrepared() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("log", "video is prepared.");
            }
        });
    }

    private void finishWithError(String report, int report_code) {
        Intent intent = new Intent();
        intent.putExtra(Utilsx.REPORT, report);
        intent.putExtra(Utilsx.REPORT_CODE, report_code);
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
