package com.skuan.javacv;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOInterruptCB;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class FFmpegTest {

    /**
     * There is no universal option for streaming timeout. Each of protocols has
     * its own list of options.
     */
    private static enum TimeoutOption {
        /**
         * Depends on protocol (FTP, HTTP, RTMP, SMB, SSH, TCP, UDP, or UNIX).
         */
        TIMEOUT,
        /**
         * Protocols
         * Maximum time to wait for (network) read/write operations to complete,
         * in microseconds.
         */
        RW_TIMEOUT,
        /**
         * Protocols -> RTSP
         * Set socket TCP I/O timeout in microseconds.
         */
        STIMEOUT;

        public String getKey() {
            return toString().toLowerCase();
        }

    }

    private static final String SOURCE_RTSP = "rtsp://192.168.10.203:51200/4k_20200702-0454_04.mp4";
    private static final int TIMEOUT = 10; // In seconds.

    public static void main(String[] args) {
        // rtspStreamingTest();
       testWithCallback(); // This is not working properly. It's just for test.
    }

    private static void rtspStreamingTest() {
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SOURCE_RTSP);
            /**
             * "timeout" - IS IGNORED when a network cable have been unplugged
             * before a connection and sometimes when connection is lost.
             *
             * "rw_timeout" - IS IGNORED when a network cable have been
             * unplugged before a connection but the option takes effect after a
             * connection was established.
             *
             * "stimeout" - works fine.
             */
            grabber.setOption(  TimeoutOption.STIMEOUT.getKey(),  String.valueOf(TIMEOUT * 1000000) ); // In microseconds.
            grabber.start();

            Frame frame = null;
            /**
             * When network is disabled (before brabber was started) grabber
             * throws exception: "org.bytedeco.javacv.FrameGrabber$Exception:
             * avformat_open_input() error -138: Could not open input...".
             *
             * When connections is lost (after a few grabbed frames)
             * grabber.grab() returns null without exception.
             */
            while ((frame = grabber.grab()) != null) {
                System.out.println("frame grabbed at " + grabber.getTimestamp());
            }
            System.out.println("loop end with frame: " + frame);
        } catch (FrameGrabber.Exception ex) {
            System.out.println("exception: " + ex);
        }
        System.out.println("end");
    }

    private static void testWithCallback() {
        try {
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            System.out.println("0000" + sdf.format(new Date()));
            final AtomicBoolean interruptFlag = new AtomicBoolean(false);
            AVIOInterruptCB.Callback_Pointer cp = new AVIOInterruptCB.Callback_Pointer() {
                @Override
                public int call(Pointer pointer) {
                    // 0 - continue, 1 - exit
                    int interruptFlagInt = interruptFlag.get() ? 1 : 0;
                    System.out.println("callback, interrupt flag == " + interruptFlagInt);
                    return interruptFlagInt;
                }

            };
            // avformat_alloc_context();
            AVIOInterruptCB cb = new AVIOInterruptCB();
            cb.callback(cp);

            System.out.println("1111" + sdf.format(new Date()));
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SOURCE_RTSP);
            grabber.setOption(  TimeoutOption.STIMEOUT.getKey(), String.valueOf(TIMEOUT * 1000000) ); // In microseconds.
            /**
             * grabber.getFormatContext() is null before grabber.start().
             *
             * But if network is disabled grabber.start() will never return.
             *
             * That's why interrupt_callback not suitable for "network disabled
             * case".
             */
            System.out.println("2222" + sdf.format(new Date()));
            grabber.start();
            System.out.println("3333" + sdf.format(new Date()));
            

            AVFormatContext oc = grabber.getFormatContext();
            oc.interrupt_callback(cb);
            System.out.println("4444" + sdf.format(new Date()));
            new Thread(new Runnable() { public void run() {
                try {
                    TimeUnit.SECONDS.sleep(TIMEOUT);
                    interruptFlag.set(true);
                    System.out.println("interrupt flag was changed");
                } catch (InterruptedException ex) {
                    System.out.println("exception in interruption thread: " + ex);
                }
            }}).start();

            Frame frame = null;
            /**
             * On one of my RTSP cams grabber stops calling callback on
             * connection lost. I think it's has something to do with message:
             * "[swscaler @ 0000000029af49e0] deprecated pixel format used, make
             * sure you did set range correctly".
             *
             * So there is at least one case when grabber stops calling
             * callback.
             */
            while ((frame = grabber.grab()) != null) {
                System.out.println("6666" + sdf.format(new Date()));
                System.out.println("frame grabbed at " + grabber.getTimestamp());
            }
            System.out.println("loop end with frame: " + frame);
        } catch (FrameGrabber.Exception ex) {
            System.out.println("exception: " + ex);
        }
        System.out.println("end");
    }
}
