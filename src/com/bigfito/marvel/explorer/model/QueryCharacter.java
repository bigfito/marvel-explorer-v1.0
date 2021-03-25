/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigfito.marvel.explorer.model;

import com.bigfito.marvel.explorer.model.utils.HashedDigest;
import com.bigfito.marvel.explorer.model.utils.TimeStampManager;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aorozco
 */
public class QueryCharacter {
    
    private final String PUBLIC_KEY = "b4f8425c4b43fb9570a14a67cf0fa8ce";
    private final String PRIVATE_KEY = "3f88f7657415b9c023ab4f10e0508eb4796d6d42";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version( HttpClient.Version.HTTP_2 )
            .build();

    private TimeStampManager timeStampForQuery;
    private HashedDigest digestForQuery;
    private List<ComicCharacter> comicCharactersArray = new ArrayList<ComicCharacter>();

    private String parameterToHash ="";

    public QueryCharacter(){

        timeStampForQuery = new TimeStampManager();
        timeStampForQuery.setTimeStamp();

        setParameterToHash( timeStampForQuery.getTimeStamp() + PRIVATE_KEY + PUBLIC_KEY );

        digestForQuery = new HashedDigest();
        digestForQuery.setHashParameter( getParameterToHash() );
        digestForQuery.setHashParameterInBytes();
        digestForQuery.processMD5Digest();

    }

    public String getParameterToHash() {
        return parameterToHash;
    }

    public void setParameterToHash(String parameterToHash) {
        this.parameterToHash = parameterToHash;
    }

    private void sendGetCommand() throws Exception {

        Map<Object, Object> queryParameters = new HashMap<>();
        queryParameters.put("ts", timeStampForQuery.getTimeStamp() );
        queryParameters.put("apikey", PUBLIC_KEY);
        queryParameters.put("hash", digestForQuery.getStringFromMD5Hash() );

        String uriBase = "https://gateway.marvel.com:443/v1/public/characters" + "?";
        String uriParameters = buildParametersFromMap( queryParameters );
        String uriResource = uriBase + uriParameters;

        HttpRequest request = HttpRequest.newBuilder()
                              .GET()
                              .uri( URI.create( uriResource ) )
                              .setHeader("User-Agent", "Marvel Comics Explorer Bot Machine")
                              .build();

        HttpResponse<String> response = httpClient.send( request, HttpResponse.BodyHandlers.ofString() );
        parseResponseBody( response );

    }

    public void parseResponseBody( HttpResponse<String> bodyContent ){

        ComicCharacter cc = new ComicCharacter();
        HttpResponse<String> responseBody = bodyContent;
        JSONObject jObjectResponse = new JSONObject( responseBody.body() );
        JSONArray jDataInResponse = jObjectResponse.getJSONObject("data").getJSONArray("results");

        for (int i = 0; i < jDataInResponse.length(); i++) {

            String idCharacter = String.valueOf( jDataInResponse.getJSONObject(i).getInt("id") );
            String nameCharacter = jDataInResponse.getJSONObject(i).getString("name").toUpperCase(Locale.ROOT);
            String descCharacter =  jDataInResponse.getJSONObject(i).getString("description").toUpperCase(Locale.ROOT);

            if ( descCharacter.equalsIgnoreCase("") ){
                descCharacter = "N/A";
            }

            String pathCharacter = jDataInResponse.getJSONObject(i).getJSONObject("thumbnail").getString("path") ;
            String extensionCharacter = jDataInResponse.getJSONObject(i).getJSONObject("thumbnail").getString("extension") ;
            String thumbnailCharacter = pathCharacter + "." + extensionCharacter;

            cc.setIdCharacter( idCharacter );
            cc.setNameCharacter( nameCharacter );
            cc.setDescriptionCharacter( descCharacter );
            cc.setThumbnailCharacter( thumbnailCharacter );

            comicCharactersArray.add(cc);

            System.out.println("ID(" + idCharacter + "): " + nameCharacter + " DESC: " + descCharacter );
        }

    }

    public List<ComicCharacter> getComicCharactersArray(){
        return this.comicCharactersArray;
    }

    public String buildParametersFromMap(Map<Object, Object> data) {

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<Object, Object> entry : data.entrySet() ) {
            if ( builder.length() > 0 ) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return builder.toString();
    }

    public static void main( String[] args ){
        QueryCharacter qc = new QueryCharacter();
        try{
            qc.sendGetCommand();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
