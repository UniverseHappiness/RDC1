package com.wuxinghua.notes.bean;

import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import javafx.collections.ObservableList;

public class NoteBean {
    private String text;
    private String result;

    private Note note;
    private ObservableList<Note> observableList;
    public String getResult() {

        return result;
    }

    public void setResult(String result) {

        this.result = result;
    }


    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }


    public NoteBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public NoteBean(String result, Note note) {
        super();
        this.result = result;
        this.note = note;
    }

    public NoteBean(String result, String text, User user) {
        super();
        this.result = result;
        this.text = text;
    }

    public NoteBean(String result, User user, ObservableList<Note> observableList){
        super();
        this.result = result;
        this.observableList = observableList;
    }

    public ObservableList<Note> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<Note> observableList) {
        this.observableList = observableList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "text='" + text + '\'' +
                ", result='" + result + '\'' +
                ", note=" + note +
                ", observableList=" + observableList +
                '}';
    }
}
