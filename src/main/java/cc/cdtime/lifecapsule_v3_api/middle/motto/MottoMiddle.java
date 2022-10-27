package cc.cdtime.lifecapsule_v3_api.middle.motto;

import cc.cdtime.lifecapsule_v3_api.meta.content.entity.Content;
import cc.cdtime.lifecapsule_v3_api.meta.content.service.IContentService;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.Motto;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoLog;
import cc.cdtime.lifecapsule_v3_api.meta.motto.entity.MottoView;
import cc.cdtime.lifecapsule_v3_api.meta.motto.service.IMottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class MottoMiddle implements IMottoMiddle {
    private final IMottoService iMottoService;
    private final IContentService iContentService;

    public MottoMiddle(IMottoService iMottoService,
                       IContentService iContentService) {
        this.iMottoService = iMottoService;
        this.iContentService = iContentService;
    }

    @Override
    public void createMotto(Motto motto) throws Exception {
        iMottoService.createMotto(motto);

        /**
         * 保存内容
         */
        Content content = new Content();
        content.setContent(motto.getContent());
        content.setIndexId(motto.getMottoId());
        iContentService.createContent(content);
    }

    @Override
    public MottoView getMotto(Map qIn, Boolean returnNull) throws Exception {
        MottoView mottoView = iMottoService.getMotto(qIn);
        if (mottoView == null) {
            if (returnNull) {
                return null;
            }
            /**
             * 没有查询到motto
             */
            throw new Exception("10060");
        }
        return mottoView;
    }

    @Override
    public void createMottoLog(MottoLog mottoLog) throws Exception {
        iMottoService.createMottoLog(mottoLog);
    }

    @Override
    public void updateMotto(Map qIn) throws Exception {
        iMottoService.updateMotto(qIn);
    }

    @Override
    public ArrayList<MottoView> listMotto(Map qIn) throws Exception {
        ArrayList<MottoView> mottoViews = iMottoService.listMotto(qIn);
        return mottoViews;
    }

    @Override
    public Integer totalMotto(Map qIn) throws Exception {
        Integer total = iMottoService.totalMotto(qIn);
        return total;
    }
}
