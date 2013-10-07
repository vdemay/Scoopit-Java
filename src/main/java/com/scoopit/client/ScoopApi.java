package com.scoopit.client;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class ScoopApi extends DefaultApi10a {

    @Override
    public String getRequestTokenEndpoint() {
        try {
            return new URL("https://www.scoop.it/oauth/request").toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        try {
            return new URL("https://www.scoop.it/oauth/authorize?oauth_token="
                    + URLEncoder.encode(requestToken.getToken(), "UTF-8")).toExternalForm();
        } catch (MalformedURLException e) {
            // cannot happen, checked exceptions like this are SHIT
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            // cannot happen, checked exceptions like this are SHIT
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAccessTokenEndpoint() {
        try {
            return new URL("https://www.scoop.it/oauth/access").toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
