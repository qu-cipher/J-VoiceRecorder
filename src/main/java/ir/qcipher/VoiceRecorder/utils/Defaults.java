package ir.qcipher.VoiceRecorder.utils;

import javax.sound.sampled.AudioFormat;

public class Defaults {
    public static final AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    public static final float SAMPLE_RATE = 16000;
    public static final int BIT_DEPTH = 16;
    public static final int MONO_CHANNELS = 1;
    public static final int FRAME_SIZE = 2 * MONO_CHANNELS;
    public static final float FRAME_RATE = SAMPLE_RATE;
    public static final boolean BIG_ENDIAN = false;
}
