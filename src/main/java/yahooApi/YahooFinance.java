package yahooApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import yahooApi.beans.Asset;
import yahooApi.beans.YahooResponse;

import javax.json.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* This class queries the Yahoo Finance API */

public class YahooFinance {

    public static final String URL_YAHOO = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s";

    /* This method requests data from the API. It is returned in form of a string. A list of Strings
    * is passed to the method which consists of tickers. */
    public String requestData(List<String> tickers) {
        //TODO improve Error Handling

        /* The list of tickers is converted to a comma separated list. */
        String symbols = String.join(",", tickers);

        /* The string query is a concatenation of the API URL and the symbols that are queried for.*/
        String query = String.format(URL_YAHOO, symbols);

        /* The query is printed on STDOUT*/
        System.out.println(query);

        /* A URL object called obj is created. Initial value is null.*/
        URL obj = null;
        try {

            /* Attempt to pass the query string to the URL object. */
            obj = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /* An HttpURLConnection object con is created */
        HttpURLConnection con = null;

        /* StringBuilder object is created.*/
        StringBuilder response = new StringBuilder();
        try {
            /* The connection is opened with the data from the URL object and the ticker info.*/
            con = (HttpURLConnection) obj.openConnection(); /* Is the space before obj legit?*/
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            /* Data is coming in. It is stored in the variable inputLine.
            StringBuilder does its magic.*/
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* The response is returned as a string.*/
        return response.toString();
    }

    /* Takes a string and returns it as a JsonObject */
    protected JsonObject convert(String jsonResponse) {
        InputStream is = new ByteArrayInputStream(jsonResponse.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject jo = reader.readObject();
        reader.close();
        return jo;
    }

    public void fetchAssetName(Asset asset) {
        YahooFinance yahoo = new YahooFinance();
        List<String> symbols = new ArrayList<>();
        symbols.add(asset.getSymbol());
        String jsonResponse = null;
        jsonResponse = yahoo.requestData(symbols);
        JsonObject jo = yahoo.convert(jsonResponse);
        asset.setName(extractName(jo));
    }

    /* A Json object like the above is passed to this function. The Map stockData maps a string
    to a Json object. This is (I guess) used for the QuoteResponse class.

     */
    private String extractName(JsonObject jo) {
        String returnName = "";
        Map<String, JsonObject> stockData = ((Map) jo.getJsonObject("quoteResponse"));
        JsonArray x = (JsonArray) stockData.get("result");
        JsonObject y = (JsonObject) x.get(0);
        JsonValue name = y.get("longName");
        returnName = name.toString();
        return returnName;
    }


    public YahooResponse getCurrentData(List<String> tickers) {
        String jsonResponse = requestData(tickers);
        ObjectMapper objectMapper = new ObjectMapper();
        YahooResponse result = null;
        try {
             result  = objectMapper.readValue(jsonResponse, YahooResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}