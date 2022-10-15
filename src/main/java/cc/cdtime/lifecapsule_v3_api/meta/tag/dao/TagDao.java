package cc.cdtime.lifecapsule_v3_api.meta.tag.dao;

import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagBase;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagNote;
import cc.cdtime.lifecapsule_v3_api.meta.tag.entity.TagView;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TagDao {
    /**
     * 在标签库里添加一个新标签
     *
     * @param tagBase
     */
    void createTagBase(TagBase tagBase);

    /**
     * 给笔记添加一个标签
     *
     * @param tagNote
     */
    void createTagNote(TagNote tagNote);

    /**
     * 修改标签热度
     *
     * @param qIn tagHot
     *            tagId
     */
    void updateTagBase(Map qIn);

    /**
     * 从标签库里物理删除一个标签
     *
     * @param qIn tagId
     *            tagName
     */
    void deleteTagBase(Map qIn);

    /**
     * 删除一个笔记的一个标签，或者删除一个笔记的所有标签
     *
     * @param qIn tagId
     *            noteId
     */
    void deleteTagNote(Map qIn);

    /**
     * 根据标签名称查询标签库记录
     *
     * @param tagName
     * @return
     */
    TagView getTagBase(String tagName);

    /**
     * 查询一个笔记的标签列表
     *
     * @param qIn noteId
     *            tagName
     *            userId
     * @return
     */
    ArrayList<TagView> listNoteTag(Map qIn);

    /**
     * 查询一批标签
     *
     * @param qIn size
     * @return
     */
    ArrayList<TagView> listBaseTag(Map qIn);

    /**
     * 聚合查询标签
     *
     * @param qIn userId
     * @return
     */
    ArrayList<TagView> listTagGroup(Map qIn);
}
