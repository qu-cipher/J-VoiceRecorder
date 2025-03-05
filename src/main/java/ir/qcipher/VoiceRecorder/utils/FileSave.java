package ir.qcipher.VoiceRecorder.utils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

public class FileSave {
    private byte[] data;
    private String filename;
    private Exts fileExtension;
    private AudioInputStream audioInputStream;

    public FileSave(byte[] data, String filename, Exts fileExtension, AudioInputStream audioInputStream) {
        this.data = data;
        this.filename = filename;
        this.fileExtension = fileExtension;
        this.audioInputStream = audioInputStream;
    }

    public String save(String directory) throws IOException {
        File path = new File(directory);
        if (!path.exists())
            path.mkdirs();

        File outputFile = new File(path, filename+"."+filename.toString());
        AudioSystem.write(
                audioInputStream,
                AudioFileFormat.Type.WAVE,
                outputFile
        );

        return outputFile.getAbsolutePath();
    }
}
