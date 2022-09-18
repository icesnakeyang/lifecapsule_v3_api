package cc.cdtime.lifecapsule_v3_api.meta.tag.service;

import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;

import java.util.ArrayList;
import java.util.Map;

public interface ITagService {
    /**
     * 在标签库里添加一个新标签
     *
     * @param tagBase
     */
    void createTagBase(TagBase tagBase) throws Exception;

    /**
     * 给笔记添加一个标签
     *
     * @param tagNote
     */
    void createTagNote(TagNote tagNote) throws Exception;

    /**
     * 修改标签热度
     *
     * @param qIn tagHot
     *            tagId
     */
    void updateTagBase(Map qIn) throws Exception;

    /**
     * 从标签库里物理删除一个标签
     *
     * @param qIn tagId
     *            tagName
     */
    void deleteTagBase(Map qIn) throws Exception;

    /**
     * 删除一个笔记的一个标签，或者删除一个笔记的所有标签
     *
     * @param qIn tagId
     *            noteId
     */
    void deleteTagNote(Map qIn) throws Exception;

    /**
     * 根据标签名称查询标签库记录
     *
     * @param tagName
     * @return
     */
    TagView getTagBase(String tagName) throws Exception;

    /**
     * 查询一个笔记的标签列表
     *
     * @param qIn noteId
     *            tagName
     * @return
     */
    ArrayList<TagView> listNoteTag(Map qIn) throws Exception;
}
