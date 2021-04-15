package com.wuxinghua.notes.service;

import com.wuxinghua.notes.dao.WriteDao;
import com.wuxinghua.notes.entity.Note;

import java.io.File;
import java.io.IOException;

public class RenameService {
        // 标题重命名
        public String rename(Note note,String title) throws IOException
        {
            WriteDao writeDao = new WriteDao();

            File f = new File(title+ ".txt");
            File newf = new File(note.getTitle()+".txt");

            if (f.exists()){
                System.out.println("旧文件存在");
            }

            if (newf.exists()) {
                System.out.println("新文件存在");
            }

            boolean renameTo = f.renameTo(newf);

            System.out.println(renameTo==true?"修改成功":"修改失败");

            writeDao.DBModifyTitle(note,title);

            String oldTitle = note.getTitle();
            return oldTitle;
        }
    }


