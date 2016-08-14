# java-midi-delayed-audio-example

This is a simple example demonstrating a [strange problem we're seeing in Alda](https://github.com/alda-lang/alda/issues/160) where audio produced by the Java MIDI Synthesizer becomes delayed by seconds after leaving a server running, closing your laptop lid, and re-opening it.

This problem may or may not be platform-specific. I am seeing this problem with my 2014 Macbook Pro, running OS X 10.9.5 Mavericks.

To run:

- Build the jar file for this example.
  - If you have [Boot](http://boot-clj.com), you can do this by running `boot build`.
- Run the jar file with `java -jar target/server.jar`.
  - (The location of the jar file may be different, depending on how you built it.)
- This will run a "server" that is really just an infinite loop that will take action whenever you press the Enter key.

  Each time you press Enter, the server will play 3 piano notes (C, D, E), roughly in time (`Thread.sleep` is not accurate), and as each note is played, the server will also print which note it is playing.

  The expected behavior is that you will hear each note and see it printed to the console at the exact same time.

To reproduce the problem:

- Run the server and press Enter a few times, just to confirm that it works correctly.
- Leave the server running, but close your laptop lid and let it go into hibernate mode.
- Re-open your laptop and try it again. If you are able to reproduce the problem, you should see the notes printed right away (roughly in time), but you will hear the audio (also roughly in time) several seconds later.
