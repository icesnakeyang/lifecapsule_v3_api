package cc.cdtime.lifecapsule_v3_api.meta.theme.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Theme {
    private Integer ids;
    private String ThemeId;
    private String themeName;
    private Date createTime;
    private String createUserId;
    private String background;
    private String blockDark;
    private String blockLight;
    private String textDark;
    private String textLight;
    private String textHolder;
    private String colorDark;
    private String colorLight;
    private String colorMedium;
    private String colorDanger;
    private String status;
    private String themeType;
}
