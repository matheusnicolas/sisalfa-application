package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model;

import java.io.Serializable;

public class User implements Serializable{

    private String userEmail;
    private int id;

    public User(String email, int id){
        this.userEmail = email;
        this.id = id;
    }

    public User(){

    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }


}
