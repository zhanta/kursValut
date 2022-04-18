package kz.demo.kursValut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.common.base.Splitter;

import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class urlConnector {
    
    public urlConnector() {}

    public static boolean isNumeric(String str) {
      try {
        Double.parseDouble(str);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public static Double cursNum(int num) { // Converts String to double
      if(num == 3) {
        String strEUR = "";
            try {
                strEUR = CursReatern(3);
            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
            };
      return Double.parseDouble(strEUR);
      }
      else if(num == 1) {
        String strRub = "";
            try {
                strRub = CursReatern(1);
            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
            };
            return Double.parseDouble(strRub);
      }
      else if(num == 2) {
        String strKZT = "";
            try {
                strKZT = CursReatern(2);
            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
            }
            return Double.parseDouble(strKZT);
      }
      return null;
    }

    // FINDS EUR KZT and RUB from json file
    public static String CursReatern(int num) throws IOException, JSONException { //1 - RUB, 2 - KZT, 3 - EUR
        JSONObject json = readJsonFromUrl("https://v6.exchangerate-api.com/v6/b4731054a5d115d6243db615/latest/USD");
            
           //System.out.println(json.get("result"));
            String textJson = json.get("conversion_rates").toString();
        
            // USED FOR DELETING BEGIN and END symbols { USD: 1, ... }
            StringBuilder sb = new StringBuilder(textJson); 
            sb.deleteCharAt(textJson.length()-1);
            sb.deleteCharAt(0);
            textJson = sb.toString();
            

            // CREATING SPLITTER FOR MAP
            Map<String, String> map = Splitter.on(",")
            .withKeyValueSeparator(":")
            .split(textJson);
           
            String cursKZT = map.get("\"KZT\""); // Getting string to KZT
            String cursRUB = map.get("\"RUB\""); // Getting string to RUB
            String cursEUR = map.get("\"EUR\""); // 
            if(num == 1) {
                return cursRUB;
            }
            if(num == 2) {
                return cursKZT;
            }
            if(num == 3) {
              return cursEUR;
            }
            return "";
    }

    // READER FOR ALL in the FILE
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
      //Connects to EXCHANGErateAPI and calls function readAll()
      public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONObject json = new JSONObject(jsonText);
          return json;
        } finally {
          is.close();
        }
    }

    
}

