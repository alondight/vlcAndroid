package com.pluxity.vlc_android;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

public class VideoStreamPlayer extends Activity {
    private final LibVLC libVlc;
    private final MediaPlayer mediaPlayer;
    private final VLCVideoLayout videoLayout;

    public VideoStreamPlayer(Context context, VLCVideoLayout videoLayout)
    {
        libVlc = new LibVLC(context);
        mediaPlayer = new MediaPlayer(libVlc);
        this.videoLayout = videoLayout;
    }

    public void start(String url)
    {
        mediaPlayer.attachViews(videoLayout, null, false, false);

        Media media = new Media(libVlc, Uri.parse(url));
        media.setHWDecoderEnabled(true, false);
        media.addOption(":skip-frames");
        media.addOption(":codec=mediacodec,iomx,all");
        media.addOption(":vcodec=h264");
        media.addOption(":rtsp-tcp");
        media.addOption(":network-caching=105");
        media.addOption(":clock-jitter=0");
        media.addOption(":clock-synchro=0");
        media.addOption(":clock-quantization=0");

        mediaPlayer.setMedia(media);
        media.release();
        mediaPlayer.play();
    }

    public void stop()
    {
        mediaPlayer.stop();
        mediaPlayer.detachViews();
    }

    public void release()
    {
        mediaPlayer.release();
        libVlc.release();
    }
}
