package com.sharmasha2nk.speech;

import java.io.File;

import com.sharmasha2nk.speech.microphone.Microphone;
import com.sharmasha2nk.speech.recognizer.Recognizer;
import com.sharmasha2nk.speech.request.Languages;
import com.sharmasha2nk.speech.response.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;

/**
 * @author Shashank Sh.
 *
 */
public class Main {

  public static void main(String[] args) {
    Microphone mic = new Microphone(FLACFileWriter.FLAC);
    File file = new File("/tmp/testfile.flac");
    try {
      mic.captureAudioToFile(file);
    } catch (Exception ex) {
      System.out.println("ERROR: Microphone is not availible.");
      ex.printStackTrace();
    }

    try {
      System.out.println("Recording...");
      Thread.sleep(5000);
      mic.close();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    mic.close();
    System.out.println("Recording stopped.");

    Recognizer recognizer = new Recognizer(Languages.ENGLISH_US, System.getProperty("GOOGLE_API_KEY"));
    try {
      int maxNumOfResponses = 1;
      System.out.println("Sample rate is: " + (int) mic.getAudioFormat().getSampleRate());
      GoogleResponse response = recognizer.getRecognizedDataForFlac(file, maxNumOfResponses, (int) mic.getAudioFormat().getSampleRate());
      System.out.println("Google Response: " + response);
    } catch (Exception ex) {
      System.out.println("ERROR: Google cannot be contacted");
      ex.printStackTrace();
    }

    file.deleteOnExit();
  }

}
