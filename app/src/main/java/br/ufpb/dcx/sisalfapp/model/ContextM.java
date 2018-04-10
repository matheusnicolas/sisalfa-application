package br.ufpb.dcx.sisalfapp.model;

public class ContextM {

    private Long id;
    private String palavra_contexto;
    private String imagem;
    private String audio;
    private String id_usuario;

    public ContextM(String palavra_contexto, String link_imagem, String audio, String id_usuario){
        this.palavra_contexto = palavra_contexto;
        this.imagem = link_imagem;
        this.audio = audio;
        this.id_usuario = id_usuario;
    }

    public ContextM(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalavra_contexto() {
        return palavra_contexto;
    }

    public void setPalavra_contexto(String palavra_contexto) {
        this.palavra_contexto = palavra_contexto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAudio(){
        return this.audio;
    }

    public void setAudio(String audio){
        this.audio = audio;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
