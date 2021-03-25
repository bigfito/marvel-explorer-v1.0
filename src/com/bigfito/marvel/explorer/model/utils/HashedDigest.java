/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigfito.marvel.explorer.model.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author aorozco
 */
public class HashedDigest {
    
    private String hashParameter = "";
    private byte[] hashParameterInBytes;
    private byte[] md5Hash;
    private final Charset UTF_8 = StandardCharsets.UTF_8;

    public HashedDigest(){

    }

    public String getHashParameter() {
        return hashParameter;
    }

    public void setHashParameter(String hashParameter) {
        this.hashParameter = hashParameter;
    }

    public void setHashParameterInBytes(){
        this.hashParameterInBytes = hashParameter.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getHashParameterInBytes(){
        return hashParameterInBytes;
    }

    public void processMD5Digest() {

        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        md5Hash = md.digest( hashParameterInBytes );
    }

    public String getStringFromMD5Hash() {

        StringBuilder sb = new StringBuilder();

        for ( byte b : md5Hash ){
            sb.append( String.format("%02x", b) );
        }
        return sb.toString();
    }
    
}
