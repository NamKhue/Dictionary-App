package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Translator {
    public static String oxfordSearch(String inputWord) {
        final String language = "en-gb";
        final String word = inputWord;
        final String fields = "definitions,pronunciations,examples";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        final String restUrl = "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
        //TODO: replace with your own app id and app key
        final String app_id = "edc73b22";
        final String app_key = "ff427a038dd2d120fad2438b264f0870";
        try {
            URL url = new URL(restUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
//            return stringBuilder.toString();
            Object obj = JSONValue.parse(stringBuilder.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray data = (JSONArray) jsonObject.get("results");
            JSONObject subJSON = (JSONObject) data.get(0);
            JSONArray data1 = (JSONArray) subJSON.get("lexicalEntries");
            JSONObject subJSON1 = (JSONObject) data1.get(0);
            JSONArray data2 = (JSONArray) subJSON1.get("entries");
            JSONObject subJSON2 = (JSONObject) data2.get(0);
            JSONArray dataDefine = (JSONArray) subJSON2.get("senses");
            JSONObject subJSON3 = (JSONObject) dataDefine.get(0);
            // Get pronunciation
            JSONArray dataAudio = (JSONArray) subJSON2.get("pronunciations");
            JSONObject OBJAudio = (JSONObject) dataAudio.get(0);
            Object pronun = OBJAudio.get("phoneticSpelling");
            String resPronunciation = "/" + pronun.toString() + "/";
            // End get pronunciation
            JSONArray data4 = (JSONArray) subJSON3.get("definitions");
            StringBuilder subDefine = new StringBuilder();
            // getExample of main meaning
            JSONArray dataExample = (JSONArray) subJSON3.get("examples");
            if(dataExample!=null){
                for (int e = 0; e < dataExample.size(); e++) {
                    JSONObject exp = (JSONObject) dataExample.get(e);
                    subDefine.append("\n\t- "+exp.get("text"));
                }
            }
            // get others of Explain
            if (subJSON3.get("subsenses") != null) {
                subDefine.append("\n\t(-) Sub Definition:");
                JSONArray array = (JSONArray) subJSON3.get("subsenses");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject subExplain = (JSONObject) array.get(i);
                    JSONArray subArray = (JSONArray) subExplain.get("definitions");
                    JSONArray subExampleArray = (JSONArray) subExplain.get("examples");
                    if (i != 0) {
                        subDefine.append("\n");
                    }
                    subDefine.append("\n\t+) SubDefinition - " + i + ": " + subArray.get(0));

                    if (subExampleArray != null) {
                        subDefine.append("\n\tExample:");
                        for (int k = 0; k < subExampleArray.size(); k++) {
                            JSONObject example = (JSONObject) subExampleArray.get(k);

                            subDefine.append("\n\t- Example - " + i + "." + k + ": " + example.get("text"));

                        }
                    }
                }
            }
            Object definition = data4.get(0);
            return "- " + inputWord.toUpperCase() + "\n(*) Pronunciation: " + resPronunciation + "\n\n(*) Definition:\n\t- This word means: " + definition + "." + subDefine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Can't find this word!";
    }
}