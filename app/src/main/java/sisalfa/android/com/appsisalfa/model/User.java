package sisalfa.android.com.appsisalfa.model;

public class User{

    private String userEmail;
    private long id;

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

    public long getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }


}
