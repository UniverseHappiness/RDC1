package com.wuxinghua.notes.controller;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.service.NoteService;


import java.io.IOException;

public class NoteController {
    private NoteService noteService = new NoteService();


    /**
     * 添加标题
     * @param title
     * @param user
     * @return
     */
    public NoteBean createNewTitle(String title, User user){

        System.out.println(title);
        return noteService.createNewTitle(title, user);

    }

    /**
     * 查看用户笔记
     * @return
     */
    public NoteBean lookNote(User user) {

        return noteService.lookNote(user);
    }

    /**
     * 插入笔记
     * @param title
     * @param user
     * @return
     * @throws IOException
     */
    public NoteBean noteInsert(String title, User user) throws IOException {

        return noteService.noteInsert(title,user);
    }
}
