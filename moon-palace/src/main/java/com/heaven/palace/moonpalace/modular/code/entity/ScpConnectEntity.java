package com.heaven.palace.moonpalace.modular.code.entity;

/**
 * @author :zhoushengen
 * @date : 2022/9/16
 */
public class ScpConnectEntity {
    private String userName;
    private String passWord;
    private String url;
    private String targetPath;

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

