package com.example.iaq_newsfeed;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NewsfeedThread extends Thread {

  static final String TAG = "NewfeedThread";
  private String recvData;
  private String keyword;

  @Override
  public void run() {
    String clientId = "5rUG8159ApgfxnJFpJmy"; //애플리케이션 클라이언트 아이디값";
    String clientSecret = "hcHltCU0LB"; //애플리케이션 클라이언트 시크릿값";
    try {
      // 검색 키워드 UTF-8로 인코딩
      String text = URLEncoder.encode(keyword, "UTF-8");
      // 원 API 주소
      //String apiURL = "https://openapi.naver.com/v1/search/news.xml"; // xml
      String apiURL = "https://openapi.naver.com/v1/search/news.json"; // json
      String param = "?query=" + text;

      // endpoint
      String endPoint = apiURL + param;

      URL url = new URL(endPoint);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("X-Naver-Client-Id", clientId);
      con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

      int responseCode = con.getResponseCode();
      BufferedReader br;
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
        response.append(inputLine);
      }
      br.close();
      recvData = response.toString();
      Log.e(TAG, response.toString());
    } catch (Exception e) {
      Log.e(TAG, e.toString());
    }
  }

  public String getRecvData() {
    return recvData;
  }

  public void setKeyword(String str) {
    keyword = str;
  }
}
