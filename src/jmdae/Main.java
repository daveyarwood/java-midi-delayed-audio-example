package jmdae;

import java.io.Console;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Main {
  public static Synthesizer getNewSynth() throws MidiUnavailableException {
    Synthesizer synth = MidiSystem.getSynthesizer();
    synth.open();
    return synth;
  }

  public static void playNote(MidiChannel channel, int noteNumber, int durationMs) {
    channel.noteOn(noteNumber, 127);
    try {
      Thread.sleep(durationMs);
    } catch (InterruptedException e) {
      System.out.println("Thread interrupted. OK, bye!");
      System.exit(1);
    }
    channel.noteOff(noteNumber);
  }

  public static void playAndPrintNotes(Synthesizer synth) {
    MidiChannel channel = synth.getChannels()[0];

    System.out.println("Playing notes...");

    int[] notes = {60, 62, 64};

    for (int note : notes) {
      System.out.println("Playing note: " + note);
      playNote(channel, note, 500);
    }

    System.out.println();
  }

  public static void main(String[] argv) {
    Console console = System.console();

    if (console == null) {
      System.out.println("ERROR: can't read keyboard input!");
      System.exit(1);
    }

    System.out.println("\"Server\" running...\n");

    while (true) {
      System.out.println("-- Press ENTER to play 3 piano notes / ^C to exit --");
      console.readPassword();

      try {
        System.out.print("Initializing MIDI synthesizer... ");
        System.out.flush();
        Synthesizer synth = getNewSynth();
        System.out.println("done.");

        playAndPrintNotes(synth);

        System.out.print("Closing synth... ");
        System.out.flush();
        synth.close();
        System.out.println("done.");
      } catch (MidiUnavailableException e) {
        System.out.println("ERROR: MIDI system unavailable.");
        System.exit(1);
      }
    }
  }
}
