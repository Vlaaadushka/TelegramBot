package ru.vlaadushka.spsuace.telegram_bot;

public class Autor {
    private String nickname = "";
    private Pars pars = new Pars();

    public String getNickname() {
        return nickname;
    }

    public Autor(String nickname) {
        this.nickname = nickname;
    }


    private String info;
    private String name;
    private String bio;
    private String url;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
