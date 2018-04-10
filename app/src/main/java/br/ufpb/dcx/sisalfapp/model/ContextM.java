package br.ufpb.dcx.sisalfapp.model;

public class ContextM {

    private Long id;
    private String audio;
    private String id_usuario;
    private String imagem;
    private String palavra_contexto;


    public ContextM(String audio, String id_usuario, String imagem, String palavra_contexto){
        this.audio = audio;
        this.id_usuario = id_usuario;
        this.imagem = imagem;
        this.palavra_contexto = palavra_contexto;
    }

    public ContextM(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPalavra_contexto() {
        return palavra_contexto;
    }

    public void setPalavra_contexto(String palavra_contexto) {
        this.palavra_contexto = palavra_contexto;
    }
}
