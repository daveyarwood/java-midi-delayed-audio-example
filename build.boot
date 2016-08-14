(set-env! :source-paths #{"src"})

(task-options!
  pom     {:project 'java-midi-delayed-audio-example
           :version "0.0.1"
           :description "Simple example demonstrating a problem re: delayed JVM MIDI audio."
           :url "https://github.com/daveyarwood/java-midi-delayed-audio-example"
           :scm {:url "https://github.com/daveyarwood/java-midi-delayed-audio-example"}
           :license {"name" "Eclipse Public License"
                     "url" "http://www.eclipse.org/legal/epl-v10.html"}}

  jar     {:file "server.jar"
           :main 'jmdae.Main}

  target  {:dir #{"target"}})

(deftask build
  "Builds an uberjar."
  []
  (comp (javac)
        (pom)
        (uber)
        (jar)
        (target)))

