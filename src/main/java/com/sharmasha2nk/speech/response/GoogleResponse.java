package com.sharmasha2nk.speech.response;

import com.google.gson.Gson;

public class GoogleResponse {
  public SpeechRecognitionResult[] results;

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
