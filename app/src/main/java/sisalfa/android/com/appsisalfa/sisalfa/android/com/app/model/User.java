package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model;

import java.io.Serializable;

public class User implements Serializable{

    private String email;
    private int id;

    public User(String email, int id){
        this.email = email;
        this.id = id;
    }

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }


}
