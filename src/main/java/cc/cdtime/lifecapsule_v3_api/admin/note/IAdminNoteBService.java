package cc.cdtime.lifecapsule_v3_api.admin.note;

import java.util.Map;

public interface IAdminNoteBService {
    /**
     * 管理员查看所有的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    Map listNote(Map in) throws Exception;
}
