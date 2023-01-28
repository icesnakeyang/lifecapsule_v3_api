package cc.cdtime.lifecapsule_v3_api.business.lastWords;

import java.util.Map;

public interface ILastWordsBService {
    void createLastWords(Map in) throws Exception;

    Map listLastWords(Map in) throws Exception;

    void saveLastWords(Map in) throws Exception;

    Map getLastWords(Map in) throws Exception;

    void deleteMyLastWords(Map in) throws Exception;
}
