package br.ufpb.dcx.sisalfapp.model;

public class Challenge {

    private long id;
    private String word;
    private String image;
    private String sound;
    private String video;
    private long authorId;

    public Challenge(long id, String word, String image, String sound, String video, long authorId) {
        this.id = id;
        this.word = word;
        this.image = image;
        this.sound = sound;
        this.video = video;
        this.authorId = authorId;
    }

    public Challenge(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
