package cc.cdtime.lifecapsule_v3_api.framework.vo;

import lombok.Data;

@Data
public class ThemeRequest extends Request {
    private String themeName;
    private String themeId;
    private String background;
    private String blockDark;
    private String blockLight;
    private String textDark;
    private String textLight;
    private String textHolder;
    private String colorDanger;
    private String colorDanger2;
}
