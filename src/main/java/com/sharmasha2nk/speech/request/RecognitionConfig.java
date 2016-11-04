package com.sharmasha2nk.speech.request;

/**
 * @author Shashank Sh.
 *
 */
public class RecognitionConfig {
  
  public String encoding;
  public int sampleRate;
  public String languageCode;
  public int maxAlternatives;
  public boolean profanityFilter;
  public SpeechContext speechContext;

}
