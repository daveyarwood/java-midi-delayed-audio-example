package jmdae;

import java.io.Console;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Main {
  public static Synthesizer synth;
  public static MidiChannel channel;

  public static void unsafeSleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      System.out.println("Thread interrupted. OK, bye!");
      System.exit(1);
    }
  }

  public static void initializeSynth() {
    try {
      synth = MidiSystem.getSynthesizer();
      synth.open();

      channel = synth.getChannels()[0];

    } catch (MidiUnavailableException e) {
      System.out.println("ERROR: MIDI system unavailable.");
      System.exit(1);
    }
  }

  public static void reopenSynth() {
    try {
      System.out.println("Restarting synth...");

      synth.close();
      synth.open();

      System.out.println("Giving it a few seconds to warm up...");
      unsafeSleep(2000);
    } catch (MidiUnavailableException e) {
      System.out.println("ERROR: MIDI system unavailable.");
      System.exit(1);
    }
  }

  public static void playNote(int noteNumber, int durationMs) {
    channel.noteOn(noteNumber, 127);
    unsafeSleep(durationMs);
    channel.noteOff(noteNumber);
  }

  public static void playAndPrintNotes() {
    System.out.println("Playing notes...");

    // close and re-open synthesizer to work around this bug
    reopenSynth();

    int[] notes = {60, 62, 64};

    for (int note : notes) {
      System.out.println("Playing note: " + note);
      playNote(note, 500);
    }

    System.out.println();
  }

  public static void main(String[] argv) {
    Console console = System.console();

    if (console == null) {
      System.out.println("ERROR: can't read keyboard input!");
      System.exit(1);
    }

    try {
      System.out.print("Initializing MIDI synthesizer... ");
      System.out.flush();
      initializeSynth();
      System.out.println("done.");

      System.out.println("\"Server\" running...\n");

      while (true) {
        System.out.println("-- Press ENTER to play 3 piano notes / ^C to exit --");
        console.readPassword();

        playAndPrintNotes();
      }
    } finally {
      synth.close();
      System.exit(0);
    }
  }
}
