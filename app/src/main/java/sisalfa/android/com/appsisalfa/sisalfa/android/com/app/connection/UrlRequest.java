package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.connection;


public class UrlRequest {

    private String getAllContextos;
    private String getAllDesafios;
    private String addContexto;
    private String addDesafio;
    private String addUser;

    public UrlRequest(){
        this.getAllContextos = "http://app.sisalfa.dcx.ufpb.br/api/getAllContext";
        this.getAllDesafios = "http://app.sisalfa.dcx.ufpb.br/api/getAllChallenge";
        this.addContexto = "http://app.sisalfa.dcx.ufpb.br/api/addContext";
        this.addDesafio = "http://app.sisalfa.dcx.ufpb.br/api/addChallenge";
        this.addUser = "http://app.sisalfa.dcx.ufpb.br/api/addUser";
    }

    public String getGetAllContextos() {
        return getAllContextos;
    }

    public void setGetAllContextos(String getAllContextos) {
        this.getAllContextos = getAllContextos;
    }

    public String getGetAllDesafios() {
        return getAllDesafios;
    }

    public void setGetAllDesafios(String getAllDesafios) {
        this.getAllDesafios = getAllDesafios;
    }

    public String getAddContexto() {
        return this.addContexto;
    }

    public void setAddContexto(String addContexto) {
        this.addContexto = addContexto;
    }

    public String getAddDesafio() {
        return this.addDesafio;
    }

    public void setAddDesafio(String addDesafio) {
        this.addDesafio = addDesafio;
    }

    public String getAddUser() {
        return this.addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }
}
