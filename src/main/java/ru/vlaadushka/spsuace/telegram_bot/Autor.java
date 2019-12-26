package ru.vlaadushka.spsuace.telegram_bot;

public class Autor {
    private String nickname = "";
    private String info;
    private String name;
    private String bio;
    private String url;

    public String getNickname() {
        return nickname;
    }

    public Autor(String nickname) {
        this.nickname = nickname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
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
