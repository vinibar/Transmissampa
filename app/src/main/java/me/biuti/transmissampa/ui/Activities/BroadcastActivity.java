package me.biuti.transmissampa.ui.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspClient;
import net.majorkernelpanic.streaming.video.VideoQuality;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import me.biuti.transmissampa.R;
import me.biuti.transmissampa.model.Broadcast;
import me.biuti.transmissampa.util.WowzaKeys;

public class BroadcastActivity extends Activity implements RtspClient.Callback,
        Session.Callback, SurfaceHolder.Callback {


    public final static String TAG = BroadcastActivity.class.getSimpleName();

    private static SurfaceView mSurfaceView;
    private static RtspClient mClient;
    // Rtsp session
    private Session mSession;
    private Broadcast mBroadcast;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_broadcast);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        mBroadcast = intent.getParcelableExtra("BROADCAST");
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(this);

        mProgressDialog = ProgressDialog.show(this, "Aguarde", "Iniciando transmissão", true);

        // Initialize RTSP client
        initRtspClient();

    }

    @Override
    protected void onResume() {
        super.onResume();

        toggleStreaming();
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            mBroadcast.getParseBroadcast().delete();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finish();
        //toggleStreaming();
    }

    private void initRtspClient() {
        // Configures the SessionBuilder
        mSession = SessionBuilder.getInstance()
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setAudioQuality(new AudioQuality(8000, 16000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(800, 480, 20, 250000))
                .setSurfaceView(mSurfaceView).setPreviewOrientation(0)
                .setCallback(this).build();

        // Configures the RTSP client
        mSurfaceView.setAspectRatioMode(SurfaceView.ASPECT_RATIO_STRETCH);
        mClient = new RtspClient();
        mClient.setSession(mSession);
        mClient.setCallback(this);
        String ip, port, path;

        // We parse the URI written in the Editext
        Pattern uri = Pattern.compile("rtsp://(.+):(\\d+)/(.+)");
        Matcher m = uri.matcher(WowzaKeys.STREAM_URL);
        m.find();
        ip = m.group(1);
        port = m.group(2);
        path = m.group(3) + ParseUser.getCurrentUser().getUsername().toString();

        mClient.setCredentials(WowzaKeys.PUBLISHER_USERNAME,
                WowzaKeys.PUBLISHER_PASSWORD);
        mClient.setServerAddress(ip, Integer.parseInt(port));
        mClient.setStreamPath("/" + path);
    }

    private void toggleStreaming() {
        if (!mClient.isStreaming()) {
            // Start camera preview
            mSession.startPreview();

            // Start video stream
            mClient.startStream();
        } else {
            // already streaming, stop streaming
            // stop camera preview
            mSession.stopPreview();

            // stop streaming
            mClient.stopStream();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mBroadcast.getParseBroadcast().delete();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mClient.release();
        mSession.release();
        mSurfaceView.getHolder().removeCallback(this);
        finish();
    }


    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        mProgressDialog.dismiss();
        switch (reason) {
            case Session.ERROR_CAMERA_ALREADY_IN_USE:
                break;
            case Session.ERROR_CAMERA_HAS_NO_FLASH:
                break;
            case Session.ERROR_INVALID_SURFACE:
                break;
            case Session.ERROR_STORAGE_NOT_READY:
                break;
            case Session.ERROR_CONFIGURATION_NOT_SUPPORTED:
                break;
            case Session.ERROR_OTHER:
                break;
        }

        if (e != null) {
            alertError(e.getMessage());
            e.printStackTrace();
        }
        finish();
    }

    private void alertError(final String msg) {
        final String error = (msg == null) ? "Unknown error: " : msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(BroadcastActivity.this);
        builder.setMessage(error).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRtspUpdate(int message, Exception exception) {
        switch (message) {
            case RtspClient.ERROR_CONNECTION_FAILED:
            case RtspClient.ERROR_WRONG_CREDENTIALS:
                alertError(exception.getMessage());
                exception.printStackTrace();
                break;
        }
    }

    @Override
    public void onPreviewStarted() {
    }

    @Override
    public void onSessionConfigured() {
    }

    @Override
    public void onSessionStarted() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "Você está transmitindo :)", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSessionStopped() {
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onBitrareUpdate(long bitrate) {
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
