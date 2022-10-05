package cc.cdtime.lifecapsule_v3_api.middle.tag;

import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import cc.cdtime.lifecapsule_v3_api.meta.tag.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TagMiddle implements ITagMiddle {
    private final ITagService iTagService;

    public TagMiddle(ITagService iTagService) {
        this.iTagService = iTagService;
    }

    @Override
    public void createTagBase(TagBase tagBase) throws Exception {
        iTagService.createTagBase(tagBase);
    }

    @Override
    public void createTagNote(TagNote tagNote) throws Exception {
        iTagService.createTagNote(tagNote);
    }

    @Override
    public void updateTagBase(Map qIn) throws Exception {
        iTagService.updateTagBase(qIn);
    }

    @Override
    public void deleteTagBase(Map qIn) throws Exception {
        iTagService.deleteTagBase(qIn);
    }

    @Override
    public void deleteTagNote(Map qIn) throws Exception {
        iTagService.deleteTagNote(qIn);
    }

    @Override
    public TagView getTagBase(String tagName, Boolean returnNull) throws Exception {
        TagView tagView = iTagService.getTagBase(tagName);
        if (tagView == null) {
            if (returnNull) {
                return null;
            }
            //没有查询到标签信息
            throw new Exception("10055");
        }
        return tagView;
    }

    @Override
    public ArrayList<TagView> listNoteTag(Map qIn) throws Exception {
        ArrayList<TagView> tagViews = iTagService.listNoteTag(qIn);
        return tagViews;
    }

    @Override
    public ArrayList<TagView> listBaseTag(Map qIn) throws Exception {
        ArrayList<TagView> tagViews = iTagService.listBaseTag(qIn);
        return tagViews;
    }
}
