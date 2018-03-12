package sisalfa.android.com.appsisalfa.model;

public class User{

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
