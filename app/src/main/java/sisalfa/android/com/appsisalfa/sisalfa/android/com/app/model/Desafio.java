package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model;


import java.io.Serializable;

public class Desafio implements Serializable{

    private long id;
    private String palavra_desafio;
    private String audio;
    private String imagem;
    private String id_usuario;

    public Desafio(String palavra_desafio, String imagem, String audio, String id_usuario){
        this.palavra_desafio = palavra_desafio;
        this.imagem = imagem;
        this.id_usuario = id_usuario;
    }

    public Desafio(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPalavra_desafio() {
        return palavra_desafio;
    }

    public void setPalavra_desafio(String palavra_desafio) {
        this.palavra_desafio = palavra_desafio;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
