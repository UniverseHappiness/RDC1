package com.wuxinghua.notes.service;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.dao.ReadDao;
import com.wuxinghua.notes.dao.WriteDao;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.ValidateUtil;

import java.io.IOException;

public class NoteService {
    private WriteDao writeDao = new WriteDao();
    private ReadDao readDao = new ReadDao();

    // 创建新标题
    public NoteBean createNewTitle(String title, User user) {

        Note note = new Note();

        if(ValidateUtil.isValidNoteTitle(title)){
            note.setTitle(title);
            NoteBean noteBean = writeDao.DBCreateWrite(note,user);

            return noteBean;
        }else{
        return new NoteBean("标题为空",null);
        }
    }

    // 查看笔记信息
    public NoteBean lookNote(User user) {

        NoteBean noteBean =readDao.allNoteInformation(user);
        return noteBean;
    }

    // 插入笔记
    public NoteBean noteInsert(String title, User user) throws IOException {

        NoteBean noteBean = readDao.noteInsert(title, user);

        return noteBean;
    }
}
