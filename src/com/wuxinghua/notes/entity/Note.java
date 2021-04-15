package com.wuxinghua.notes.entity;

public class Note {

    private int like; // 点赞数

    private int noteId; // 笔记id

    private String title; // 标题

    private String userName; // 笔记用户名
    private String noteText;// 笔记内容

    private String createTime; // 笔记创建时间
    private String updateTime;// 笔记更新时间

    private boolean visibility; // 可见性



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Note{" +
                "like=" + like +
                ", noteId=" + noteId +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", noteText='" + noteText + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", visibility=" + visibility +
                '}';
    }
}
