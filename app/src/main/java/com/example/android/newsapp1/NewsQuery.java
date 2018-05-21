package com.example.android.newsapp1;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class NewsQuery {

    private static final String LOG_DATA = NewsQuery.class.getSimpleName();

    public NewsQuery() {
    }

    public static List<NewsModel> getNewsData(String requestUrl) {

        //from string to url
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            //from url to json data
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_DATA, "Cant make HTTP request", e);
        }

        // Return the list extracted from json data
        return extractFeatureFromJson(jsonResponse);
    }

    //get string return URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_DATA, "Cant create URL ", e);
        }
        return url;
    }

    //get URL return json data in string
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful -> response code 200 - get stream and read data
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_DATA, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_DATA, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {

                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<NewsModel> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding articles to
        List<NewsModel> articles = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create JSONObjects from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            // Extract the JSONArray associated with the key called "results",
            // which represents a list of results (or articles).
            JSONArray resultArray = response.getJSONArray("results");
            // For each articles in the articleArray, create an {@link Article} object
            for (int i = 0; i < resultArray.length(); i++) {
                // Get a single article at position i within the list of articles
                JSONObject currentArticle = resultArray.getJSONObject(i);
                // extract title of the article
                String title = currentArticle.getString("webTitle").trim();
                if (title.contains("|")) { // that means that the author name appears in the title - no need for it
                    String[] arrayString = title.split("\\|");
                    title = arrayString[0].trim(); // only put the part before the | in the title
                }
                // extract date of the article
                String date = currentArticle.getString("webPublicationDate").trim();
                // Extract the url of the article
                String url = currentArticle.getString("webUrl").trim();
                // Extract the section of the article
                String section = currentArticle.getString("sectionName").trim();
                // For a given article, extract the JSONArray associated with the
                // key called "tags", that has the name of the author in it
                JSONArray tags = currentArticle.getJSONArray("tags");
                String author;
                if (tags.length()!=0) {
                    JSONObject tagsObject = tags.getJSONObject(0);
                    author = tagsObject.getString("webTitle").trim();
                } else author = "";
                // Create a new {@link Article} object with the title, date, author, section
                // and url from the JSON response.
                NewsModel article = new NewsModel(title, date, author, section, url);
                // Add the new {@link Article} to the list of articles.
                articles.add(article);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        return articles;
    }
}