package cc.cdtime.lifecapsule_v3_api.meta.antiDelay.service;

import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.dao.AntiDelayDao;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayNote;
import cc.cdtime.lifecapsule_v3_api.meta.antiDelay.entity.AntiDelayView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class AntiDelayNoteService implements IAntiDelayNoteService {
    private final AntiDelayDao antiDelayDao;

    public AntiDelayNoteService(AntiDelayDao antiDelayDao) {
        this.antiDelayDao = antiDelayDao;
    }

    @Override
    public void createAntiDelayNote(AntiDelayNote antiDelayNote) throws Exception {
        antiDelayDao.createAntiDelayNote(antiDelayNote);
    }

    @Override
    public ArrayList<AntiDelayView> listAntiDelayNote(Map qIn) throws Exception {
        ArrayList<AntiDelayView> antiDelayViews = antiDelayDao.listAntiDelayNote(qIn);
        return antiDelayViews;
    }

    @Override
    public AntiDelayView getAntiDelayNote(String antiDelayId) throws Exception {
        AntiDelayView antiDelayView = antiDelayDao.getAntiDelayNote(antiDelayId);
        return antiDelayView;
    }

    @Override
    public void deleteAntiDelayNote(String noteId) throws Exception {
        antiDelayDao.deleteAntiDelayNote(noteId);
    }

    @Override
    public AntiDelayView loadAntiDelayNote(Map qIn) throws Exception {
        AntiDelayView antiDelayView = antiDelayDao.loadAntiDelayNote(qIn);
        return antiDelayView;
    }
}
