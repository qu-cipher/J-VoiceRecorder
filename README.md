## VoiceRecoder console app in Java
this is a simple voice recorder implementation in java

## Usage
- Clone this repo
```bash
git clone https://github.com/qu-cipher/J-VoiceRecorder.git
```
- Build `jar` on your own
```bash
gradle jar
```
- Alternatively, You can download the prebuild `jar` from [Releases](https://github.com/qu-cipher/J-VoiceRecorder/releases)
- Import it, Use it.
```java
import ir.qcipher.VoiceRecorder.Recorder;

class MyAwesomeApp {
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
```
