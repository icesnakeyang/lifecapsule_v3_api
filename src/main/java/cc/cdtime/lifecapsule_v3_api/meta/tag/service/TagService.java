package cc.cdtime.lifecapsule_v3_api.meta.tag.service;

import cc.cdtime.lifecapsule_v3_api.meta.tag.dao.TagDao;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TagService implements ITagService {
    private final TagDao tagDao;

    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void createTagBase(TagBase tagBase) throws Exception {
        tagDao.createTagBase(tagBase);
    }

    @Override
    public void createTagNote(TagNote tagNote) throws Exception {
        tagDao.createTagNote(tagNote);
    }

    @Override
    public void updateTagBase(Map qIn) throws Exception {
        tagDao.updateTagBase(qIn);
    }

    @Override
    public void deleteTagBase(Map qIn) throws Exception {
        tagDao.deleteTagBase(qIn);
    }

    @Override
    public void deleteTagNote(Map qIn) throws Exception {
        String tagId = (String) qIn.get("tagId");
        String noteId = (String) qIn.get("noteId");
        if (tagId == null && noteId == null) {
            /**
             * 标签Id或者笔记Id至少要指定一个
             */
            throw new Exception("10054");
        }
        tagDao.deleteTagNote(qIn);
    }

    @Override
    public TagView getTagBase(String tagName) throws Exception {
        TagView tagView = tagDao.getTagBase(tagName);
        return tagView;
    }

    @Override
    public ArrayList<TagView> listNoteTag(Map qIn) throws Exception {
        ArrayList<TagView> tagViews = tagDao.listNoteTag(qIn);
        return tagViews;
    }
}
