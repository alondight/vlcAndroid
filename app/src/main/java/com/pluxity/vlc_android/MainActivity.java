package com.pluxity.vlc_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.videolan.libvlc.util.VLCVideoLayout;


public class MainActivity extends Activity {

    private static final String[] urlList = {
            "rtsp://210.99.70.120:1935/live/cctv001.stream",
            "rtsp://210.99.70.120:1935/live/cctv002.stream"
    };
    private static final String[] cameraList = {
            "Camera 1",
            "Camera 2"
    };

    private VideoStreamPlayer streamPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VLCVideoLayout videoLayout = findViewById(R.id.videoLayout);
        streamPlayer = new VideoStreamPlayer(this, videoLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                cameraList
        );

        Spinner dropdown = findViewById(R.id.cameraDropdown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id)
            {
                streamPlayer.stop();
                streamPlayer.start(urlList[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

   /* @Override
    protected void onStart()
    {
        super.onStart();

        ArrayList<String> options = new ArrayList<String>();
        options.add("--aout=opensles");
        options.add("--audio-time-stretch"); // time stretching
        options.add("-vvv"); // verbosity
        libVlc = new LibVLC(MainActivity.this, options);
        media.addOption("--aout=opensles");
        media.addOption("--audio-time-stretch"); // time stretching
        media.addOption("-vvv"); // verbosity
        media.addOption("--aout=opensles");
        media.addOption("--avcodec-codec=h264");
        media.addOption("--file-logging");
        media.addOption("--logfile=vlc-log.txt");
        int cache = 1500;
        media.addOption(":network-caching=" + cache);
        media.addOption(":file-caching=" + cache);
        media.addOption(":live-cacheing=" + cache);
        media.addOption(":sout-mux-caching=" + cache);
        media.addOption(":codec=mediacodec,iomx,all");

    }*/


    @Override
    protected void onStop()
    {
        super.onStop();
        streamPlayer.stop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        streamPlayer.release();
    }


}