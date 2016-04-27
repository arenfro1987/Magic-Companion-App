package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Derek on 11/9/2015.
 */
public class ApiConnector extends AsyncTask<String, Void, ArrayList<Card>> {
    private InputStream stream;
    private OnDataSendToActivity dataSendToActivity;
    private HttpClient client;
    private HttpUriRequest request;
    private ArrayList<Card> theList = new ArrayList<>();

    public ApiConnector(Activity activity) {
        client = new DefaultHttpClient();
        dataSendToActivity = (OnDataSendToActivity) activity;
    }

    @Override
    protected ArrayList<Card> doInBackground(String... params) {
        String searchTerm = params[0];
        searchTerm = searchTerm.replace(" ", "+");


        HttpURLConnection urlConnection = null;
        try {
            request = new HttpGet("http://api.deckbrew.com/mtg/cards?name=" + searchTerm);
            HttpResponse httpResponse = client.execute(request);
            stream = httpResponse.getEntity().getContent();
            if (stream != null) {
                jsonRead(stream);
            }

            for (int x = 0; x < theList.size(); x++) {
                Card theCard = theList.get(x);
                String name = theCard.getName();
                String set = theCard.getMyEdition().getSet();
                name = name.replace(" ", "+");
                set = set.replace(" ", "+");
                request = new HttpGet("http://partner.tcgplayer.com/x3/phl.asmx/p?pk=DERENFRO&s=" + set + "&p=" + name);
                httpResponse = client.execute(request);
                stream = httpResponse.getEntity().getContent();
                theList.set(x, setPrice(stream, theCard));

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        return theList;
    }


    private ArrayList<Card> jsonRead(InputStream stream) throws IOException, JSONException {

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        //ArrayList<Card> cards = new ArrayList<>();
        String line = "";
        String result = "";
        while ((line = br.readLine()) != null) {
            result += line;
        }


        stream.close();
        JSONArray array = new JSONArray(result);

        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.getJSONObject(i);
            ArrayList<Card> tempArray = new ArrayList<>();
            tempArray = generateCard(item);
            theList.addAll(tempArray);

        }

        br.close();
        return theList;
    }

    private Card setPrice(InputStream stream, Card card) throws JSONException, IOException, XmlPullParserException {
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        //ArrayList<Card> cards = new ArrayList<>();
        String line = "";
        String result = "";
        while((line = br.readLine()) != null)
        {
            result += line;
        }
        stream.close();

        JSONObject item = new JSONObject(result);
        */

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(stream, null);
        parser.nextTag();
        card = parseXML(parser, card);

        return card;
    }

    private Card parseXML(XmlPullParser parser, Card card) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();


        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("hiprice")) {
                        card.getMyEdition().setHighPrice(parser.nextText());
                    } else if (name.equals("lowprice")) {
                        card.getMyEdition().setLowPrice(parser.nextText());
                    } else if (name.equals("avgprice")) {
                        card.getMyEdition().setAvgPrice(parser.nextText());
                    }
                    else if (name.equals("foilavgprice")) {
                        card.getMyEdition().setAvgFoilPrice(parser.nextText());
                    }


                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
            }
            eventType = parser.next();
        }

        if(card.getMyEdition().getLowPrice() == null) {
            //need to populate the data so it isn't null
            card.getMyEdition().setLowPrice("0.00");
        }
        if(card.getMyEdition().getAvgPrice() == null) {
            card.getMyEdition().setAvgPrice("0.00");
        }
        if(card.getMyEdition().getHighPrice() == null) {
            card.getMyEdition().setHighPrice("0.00");
        }
        if(card.getMyEdition().getAvgFoilPrice() == null) {
            card.getMyEdition().setAvgFoilPrice("0.00");
        }
        return card;

    }


    private ArrayList<Card> generateCard(JSONObject object) throws JSONException {
        ArrayList<Card> temp = new ArrayList<>();
        String name = "";
        JSONArray types = new JSONArray();
        JSONArray colors = new JSONArray();
        String convertedManaCost = "";
        String cost = "";
        String formats = "";
        String text = "";
        JSONArray editions = new JSONArray();
        if (object.has("name")) {
            name = object.getString("name");
        }
        if (object.has("types")) {
            types = object.getJSONArray("types");
        }
        if (object.has("colors")) {
            colors = object.getJSONArray("colors");
        }
        if (object.has("cmc")) {
            convertedManaCost = object.getString("cmc");
        }
        if (object.has("text")) {
            text = object.getString("text");
        }
        if (object.has("cost")) {
            cost = object.getString("cost");
        }
        if (object.has("formats")) {
            formats = object.getString("formats");
        }
        if (object.has("editions")) {
            editions = object.getJSONArray("editions");
        }
        ArrayList<String> typesArray = new ArrayList<>();
        ArrayList<String> colorsArray = new ArrayList<>();

        for (int x = 0; x < types.length(); x++) {
            typesArray.add((String) types.get(x));
        }
        for (int x = 0; x < colors.length(); x++) {
            colorsArray.add((String) colors.get(x));
        }
        for (int x = 0; x < editions.length(); x++) {
            Edition edition = new Edition();
            JSONObject currentEdition = editions.getJSONObject(x);
            if (currentEdition.has("artist")) {
                edition.setArtist(currentEdition.getString("artist"));
            }
            if (currentEdition.has("flavor")) {
                edition.setFlavor(currentEdition.getString("flavor"));
            }
            if (currentEdition.has("rarity")) {
                edition.setRarity(currentEdition.getString("rarity"));
            }
            if (currentEdition.has("set")) {
                edition.setSet(currentEdition.getString("set"));
            }
            if (currentEdition.has("set_id")) {
                edition.setSetID(currentEdition.getString("set_id"));
            }
            if (currentEdition.has("store_url")) {
                edition.setStoreURL(currentEdition.getString("store_url"));
            }
            if (currentEdition.has("image_url")) {
                edition.setImageURL(currentEdition.getString("image_url"));
            }

            Card tempCard = new Card(name, convertedManaCost, edition, typesArray, colorsArray, cost, formats, text);
            temp.add(tempCard);
        }

        return temp;
    }

    @Override
    protected void onPostExecute(ArrayList<Card> cards) {
        dataSendToActivity.sendData(theList);
        super.onPostExecute(cards);

    }
}
