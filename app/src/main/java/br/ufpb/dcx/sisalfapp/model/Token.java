package br.ufpb.dcx.sisalfapp.model;

/**
 * Created by Pichau on 01/06/2018.
 */

public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public Token(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
