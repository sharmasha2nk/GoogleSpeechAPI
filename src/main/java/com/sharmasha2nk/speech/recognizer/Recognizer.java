package com.sharmasha2nk.speech.recognizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.sharmasha2nk.speech.request.GoogleRequest;
import com.sharmasha2nk.speech.request.Languages;
import com.sharmasha2nk.speech.request.RecognitionAudio;
import com.sharmasha2nk.speech.request.RecognitionConfig;
import com.sharmasha2nk.speech.response.GoogleResponse;

/**
 * @author Shashank Sh.
 *
 */
public class Recognizer {

  /**
   * URL to POST audio data and retrieve results
   */
  private static final String GOOGLE_RECOGNIZER_URL = "https://speech.googleapis.com/v1beta1/speech:syncrecognize";

  private boolean profanityFilter = true;
  private String language = null;
  private String apikey = null;

  /**
   * @param englishUs
   * @param apikey
   */
  public Recognizer(Languages englishUs, String apikey) {
    this.language = englishUs.toString();
    this.apikey = apikey;
  }

  /**
   * @param file
   * @param maxNumOfResponses
   * @param sampleRate
   * @return
   * @throws IOException
   */
  public GoogleResponse getRecognizedDataForFlac(File flacFile, int maxNumOfResponses, int sampleRate) throws IOException {
    GoogleResponse response = rawRequest(flacFile, maxNumOfResponses, sampleRate);
    return response;
  }


  /**
   * Performs the request to Google with a file <br>
   * Request is buffered
   *
   * @param inputFile Input files to recognize
   * @return Returns the raw, unparsed response from Google
   * @throws IOException Throws exception if something went wrong
   */
  private GoogleResponse rawRequest(File inputFile, int maxResults, int sampleRate) throws IOException {
    GoogleRequest googleRequest = new GoogleRequest();
    RecognitionConfig recognitionConfig = new RecognitionConfig();
    recognitionConfig.encoding = "FLAC";
    recognitionConfig.languageCode = language;
    recognitionConfig.maxAlternatives = maxResults;
    recognitionConfig.profanityFilter = profanityFilter;
    recognitionConfig.sampleRate = sampleRate;
    googleRequest.config = recognitionConfig;

    RecognitionAudio recognitionAudio = new RecognitionAudio();
    String encodedBase64 = null;
    try (FileInputStream fileInputStreamReader = new FileInputStream(inputFile)) {
      byte[] bytes = new byte[(int) inputFile.length()];
      fileInputStreamReader.read(bytes);
      encodedBase64 = new String(Base64.encodeBase64(bytes));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    recognitionAudio.content = encodedBase64;
    googleRequest.audio = recognitionAudio;

    Gson gson = new Gson();
    String json = gson.toJson(googleRequest);
    System.out.println("Request:" + json);

    URL url = new URL(GOOGLE_RECOGNIZER_URL + "?key=" + apikey);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setConnectTimeout(50000);// 50 secs
    connection.setReadTimeout(50000);// 50 secs

    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json");

    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
    out.write(json);
    out.flush();
    out.close();

    int res = connection.getResponseCode();
    if (res == 200) {
      InputStream is = connection.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      return gson.fromJson(br, GoogleResponse.class);
    } else {
      throw new IOException("Response is not 200 but " + res);
    }
  }

}
