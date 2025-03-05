package ir.qcipher.VoiceRecorder;

import javax.sound.sampled.*;
import java.io.*;

import ir.qcipher.VoiceRecorder.utils.Exts;
import ir.qcipher.VoiceRecorder.utils.FileSave;
import static ir.qcipher.VoiceRecorder.utils.Defaults.*;

/**
 * <h1>Recorder</h1>
 *
 * @author qu-cipher
 */
public class Recorder {
    private TargetDataLine microphone;
    private ByteArrayOutputStream recordingBuffer;
    private boolean isRecording;

    /**
     * <h1>record()</h1>
     *
     * method to start the voice recording
     */
    public void record() {
        // Create audio format
        AudioFormat format = new AudioFormat(
                ENCODING,
                SAMPLE_RATE,
                BIT_DEPTH,
                MONO_CHANNELS,
                FRAME_SIZE,
                FRAME_RATE,
                BIG_ENDIAN
        );

        try {
            microphone = AudioSystem.getTargetDataLine(format); // Get microphone input line
            microphone.open(format);
            microphone.start();

            recordingBuffer = new ByteArrayOutputStream(); // Initialize recording buffer
            isRecording = true;

            Thread recordingThread = new Thread(() -> {
                byte[] buffer = new byte[4096];
                while (isRecording) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        recordingBuffer.write(buffer, 0, bytesRead);
                    }
                }
            });
            recordingThread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h1>stopRecording()</h1>
     *
     * method to stop the current recording progress
     */
    public void stopRecording() {
        if (microphone != null
                && isRecording) {
            isRecording = false;
            microphone.stop();
            microphone.close();
        }
    }

    /**
     * <h1>getRecordedAudio()</h1>
     *
     * method to get the recorded data in <code>byte[]</code>
     *
     * @return byte[]
     */
    public byte[] getRecordedAudio() {
        return recordingBuffer.toByteArray();
    }

    /**
     * <h1>saveToWavFile()</h1>
     *
     * alternative method to save the recorded audio
     */
    public void saveToWavFile() {
        AudioFormat format = new AudioFormat(
                ENCODING,
                SAMPLE_RATE,
                BIT_DEPTH,
                MONO_CHANNELS,
                FRAME_SIZE,
                FRAME_RATE,
                BIG_ENDIAN
        );

        byte[] audioData = getRecordedAudio();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
             AudioInputStream audioInputStream = new AudioInputStream(
                     bais,
                     format,
                     audioData.length / format.getFrameSize()
             )) {
            FileSave fs = new FileSave(audioData,
                    "audio_"+System.currentTimeMillis(),
                    Exts.WAV,
                    audioInputStream);

            System.out.println("Audio saved to: "+fs.save("records"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Recorder rc = new Recorder();
        rc.record();
        try {
            Thread.sleep(10000); // Record for 10 seconds
            rc.stopRecording();


            rc.saveToWavFile(); // Save directly using built-in method
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}