package br.ufpb.dcx.sisalfapp.model;

public class ContextM {

    private long id;
    private String name;
    private String image;
    private String sound;
    private String video;

    public ContextM(Long id, String name, String image, String sound, String video) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.sound = sound;
        this.video = video;
    }

    public ContextM(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
