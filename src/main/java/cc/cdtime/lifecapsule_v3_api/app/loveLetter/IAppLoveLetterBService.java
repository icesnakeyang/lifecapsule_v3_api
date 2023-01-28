package cc.cdtime.lifecapsule_v3_api.app.loveLetter;

import java.util.Map;

public interface IAppLoveLetterBService {
    Map listLoveLetter(Map in) throws Exception;

    Map getLoveLetter(Map in) throws Exception;

    void saveLoveLetter(Map in) throws Exception;

    void deleteMyLoveLetter(Map in) throws Exception;
}
