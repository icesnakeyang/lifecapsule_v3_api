package cc.cdtime.lifecapsule_v3_api.admin.theme;

import cc.cdtime.lifecapsule_v3_api.framework.constant.ESTags;
import cc.cdtime.lifecapsule_v3_api.framework.tools.GogoTools;
import cc.cdtime.lifecapsule_v3_api.meta.admin.entity.AdminUserView;
import cc.cdtime.lifecapsule_v3_api.meta.theme.entity.Theme;
import cc.cdtime.lifecapsule_v3_api.middle.admin.IAdminUserMiddle;
import cc.cdtime.lifecapsule_v3_api.middle.theme.IThemeMiddle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminThemeBService implements IAdminThemeBService {
    private final IAdminUserMiddle iAdminUserMiddle;
    private final IThemeMiddle iThemeMiddle;

    public AdminThemeBService(IAdminUserMiddle iAdminUserMiddle,
                              IThemeMiddle iThemeMiddle) {
        this.iAdminUserMiddle = iAdminUserMiddle;
        this.iThemeMiddle = iThemeMiddle;
    }

    @Override
    public Map listWebTheme(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("themeType", ESTags.WEB_CLIENT);
        ArrayList<Theme> themes = iThemeMiddle.listTheme(qIn);

        Map out = new HashMap();
        out.put("themeList", themes);

        return out;
    }

    @Override
    public void createWebTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeName = in.get("themeName").toString();
        String background = (String) in.get("background");
        String blockDark = (String) in.get("blockDark");
        String blockLight = (String) in.get("blockLight");
        String textDark = (String) in.get("textDark");
        String textLight = (String) in.get("textLight");
        String textHolder = (String) in.get("textHolder");
        String colorDanger = (String) in.get("colorDanger");
        String colorDanger2 = (String) in.get("colorDanger2");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        Theme theme = new Theme();
        theme.setThemeId(GogoTools.UUID32());
        theme.setThemeType(ESTags.WEB_CLIENT.toString());
        theme.setThemeName(themeName);
        theme.setCreateTime(new Date());
        theme.setCreateUserId(adminUserView.getAdminId());
        theme.setStatus(ESTags.ACTIVE.toString());
        theme.setBackground(background);
        theme.setBlockDark(blockDark);
        theme.setBlockLight(blockLight);
        theme.setTextLight(textLight);
        theme.setTextDark(textDark);
        theme.setTextHolder(textHolder);
        theme.setColorDanger(colorDanger);
        theme.setColorDanger2(colorDanger2);
        iThemeMiddle.createTheme(theme);
    }

    @Override
    public Map getWebTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeId = in.get("themeId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("themeType", ESTags.WEB_CLIENT);
        Theme theme = iThemeMiddle.getTheme(qIn, false);

        Map out = new HashMap();
        out.put("theme", theme);

        return out;
    }

    @Override
    public void updateWebTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeId = in.get("themeId").toString();
        String background = (String) in.get("background");
        String blockDark = (String) in.get("blockDark");
        String blockLight = (String) in.get("blockLight");
        String textDark = (String) in.get("textDark");
        String textLight = (String) in.get("textLight");
        String textHolder = (String) in.get("textHolder");
        String themeName = (String) in.get("themeName");
        String colorDanger = (String) in.get("colorDanger");
        String colorDanger2 = (String) in.get("colorDanger2");
        String colorDark2 = (String) in.get("colorDark2");
        String colorMedium2 = (String) in.get("colorMedium2");
        String colorLight2 = (String) in.get("colorLight2");

        int cc = 0;
        Map qIn = new HashMap();
        if (background != null) {
            qIn.put("background", background);
            cc++;
        }
        if (blockDark != null) {
            qIn.put("blockDark", blockDark);
            cc++;
        }
        if (blockLight != null) {
            qIn.put("blockLight", blockLight);
            cc++;
        }
        if (textDark != null) {
            qIn.put("textDark", textDark);
            cc++;
        }
        if (textLight != null) {
            qIn.put("textLight", textLight);
            cc++;
        }
        if (textHolder != null) {
            qIn.put("textHolder", textHolder);
            cc++;
        }
        if (themeName != null) {
            qIn.put("themeName", themeName);
            cc++;
        }
        if (colorDanger != null) {
            qIn.put("colorDanger", colorDanger);
            cc++;
        }
        if (colorDanger2 != null) {
            qIn.put("colorDanger2", colorDanger2);
            cc++;
        }
        if (colorDark2 != null) {
            qIn.put("colorDark2", colorDark2);
            cc++;
        }
        if (colorMedium2 != null) {
            qIn.put("colorMedium2", colorMedium2);
            cc++;
        }
        if (colorLight2 != null) {
            qIn.put("colorLight2", colorLight2);
            cc++;
        }
        if (cc == 0) {
            return;
        }

        qIn.put("themeId", themeId);

        Map qIn2 = new HashMap();
        qIn2.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn2, false);

        iThemeMiddle.updateTheme(qIn);
    }

    @Override
    public void createAppTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeName = in.get("themeName").toString();
        String background = (String) in.get("background");
        String blockDark = (String) in.get("blockDark");
        String blockLight = (String) in.get("blockLight");
        String textDark = (String) in.get("textDark");
        String textLight = (String) in.get("textLight");
        String textHolder = (String) in.get("textHolder");
        String colorDanger = (String) in.get("colorDanger");
        String colorDanger2 = (String) in.get("colorDanger2");
        String colorDark = (String) in.get("colorDark");
        String colorDark2 = (String) in.get("colorDark2");
        String colorMedium = (String) in.get("colorMedium");
        String colorMedium2 = (String) in.get("colorMedium2");
        String colorLight = (String) in.get("colorLight");
        String colorLight2 = (String) in.get("colorLight2");
        String color1 = (String) in.get("color1");
        String color2 = (String) in.get("color2");
        String color3 = (String) in.get("color3");
        String color4 = (String) in.get("color4");
        String status = (String) in.get("status");

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        Theme theme = new Theme();
        theme.setThemeId(GogoTools.UUID32());
        theme.setThemeType(ESTags.MOBILE_CLIENT.toString());
        theme.setThemeName(themeName);
        theme.setCreateTime(new Date());
        theme.setCreateUserId(adminUserView.getAdminId());
        theme.setStatus(ESTags.ACTIVE.toString());
        theme.setBackground(background);
        theme.setBlockDark(blockDark);
        theme.setBlockLight(blockLight);
        theme.setTextLight(textLight);
        theme.setTextDark(textDark);
        theme.setTextHolder(textHolder);
        theme.setColorDanger(colorDanger);
        theme.setColorDanger2(colorDanger2);
        theme.setColorDark(colorDark);
        theme.setColorDark2(colorDark2);
        theme.setColorMedium(colorMedium);
        theme.setColorMedium2(colorMedium2);
        theme.setColorLight(colorLight);
        theme.setColorLight2(colorLight2);
        theme.setColor1(color1);
        theme.setColor2(color2);
        theme.setColor3(color3);
        theme.setColor4(color4);
        iThemeMiddle.createTheme(theme);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAppTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeId = in.get("themeId").toString();
        String background = (String) in.get("background");
        String blockDark = (String) in.get("blockDark");
        String blockLight = (String) in.get("blockLight");
        String textDark = (String) in.get("textDark");
        String textLight = (String) in.get("textLight");
        String textHolder = (String) in.get("textHolder");
        String themeName = (String) in.get("themeName");
        String colorDanger = (String) in.get("colorDanger");
        String colorDanger2 = (String) in.get("colorDanger2");
        String colorDark = (String) in.get("colorDark");
        String colorDark2 = (String) in.get("colorDark2");
        String colorMedium = (String) in.get("colorMedium");
        String colorMedium2 = (String) in.get("colorMedium2");
        String colorLight = (String) in.get("colorLight");
        String colorLight2 = (String) in.get("colorLight2");
        String color1 = (String) in.get("color1");
        String color2 = (String) in.get("color2");
        String color3 = (String) in.get("color3");
        String color4 = (String) in.get("color4");
        String status = (String) in.get("status");

        Map qIn = new HashMap();
        qIn.put("themeId", themeId);
        Theme theme = iThemeMiddle.getTheme(qIn, false);

        int cc = 0;
        qIn = new HashMap();
        if (background != null) {
            if (theme.getBackground() != null) {
                if (!background.equals(theme.getBackground())) {
                    qIn.put("background", background);
                    cc++;
                }
            } else {
                qIn.put("background", background);
                cc++;
            }
        }
        if (blockDark != null) {
            if (theme.getBlockDark() != null) {
                if (!blockDark.equals(theme.getBlockDark())) {
                    qIn.put("blockDark", blockDark);
                    cc++;
                }
            } else {
                qIn.put("blockDark", blockDark);
                cc++;
            }
        }
        if (blockLight != null) {
            if (theme.getBlockLight() != null) {
                if (!blockLight.equals(theme.getBlockLight())) {
                    qIn.put("blockLight", blockLight);
                    cc++;
                }
            } else {
                qIn.put("blockLight", blockLight);
                cc++;
            }
        }
        if (textDark != null) {
            if (theme.getTextDark() != null) {
                if (!textDark.equals(theme.getTextDark())) {
                    qIn.put("textDark", textDark);
                    cc++;
                }
            } else {
                qIn.put("textDark", textDark);
                cc++;
            }
        }
        if (textLight != null) {
            if (theme.getTextLight() != null) {
                if (!textLight.equals(theme.getTextLight())) {
                    qIn.put("textLight", textLight);
                    cc++;
                }
            } else {
                qIn.put("textLight", textLight);
                cc++;
            }
        }
        if (textHolder != null) {
            if (theme.getTextHolder() != null) {
                if (!textHolder.equals(theme.getTextHolder())) {
                    qIn.put("textHolder", textHolder);
                    cc++;
                }
            } else {
                qIn.put("textHolder", textHolder);
                cc++;
            }
        }
        if (themeName != null) {
            if (theme.getThemeName() != null) {
                if (!themeName.equals(theme.getThemeName())) {
                    qIn.put("themeName", themeName);
                    cc++;
                }
            } else {
                qIn.put("themeName", themeName);
                cc++;
            }
        }
        if (colorDanger != null) {
            if (theme.getColorDanger() != null) {
                if (!colorDanger.equals(theme.getColorDanger())) {
                    qIn.put("colorDanger", colorDanger);
                    cc++;
                }
            } else {
                qIn.put("colorDanger", colorDanger);
                cc++;
            }
        }
        if (colorDanger2 != null) {
            if (theme.getColorDanger2() != null) {
                if (!colorDanger2.equals(theme.getColorDanger2())) {
                    qIn.put("colorDanger2", colorDanger2);
                    cc++;
                }
            } else {
                qIn.put("colorDanger2", colorDanger2);
                cc++;
            }
        }
        if (colorDark != null) {
            if (theme.getColorDark() != null) {
                if (!colorDark.equals(theme.getColorDark())) {
                    qIn.put("colorDark", colorDark);
                    cc++;
                }
            } else {
                qIn.put("colorDark", colorDark);
                cc++;
            }
        }
        if (colorDark2 != null) {
            if (theme.getColorDark2() != null) {
                if (!colorDark2.equals(theme.getColorDark2())) {
                    qIn.put("colorDark2", colorDark2);
                    cc++;
                }
            } else {
                qIn.put("colorDark2", colorDark2);
                cc++;
            }
        }
        if (colorMedium != null) {
            if (theme.getColorMedium() != null) {
                if (!colorMedium.equals(theme.getColorMedium())) {
                    qIn.put("colorMedium", colorMedium);
                    cc++;
                }
            } else {
                qIn.put("colorMedium", colorMedium);
                cc++;
            }
        }
        if (colorMedium2 != null) {
            if (theme.getColorMedium2() != null) {
                if (!colorMedium2.equals(theme.getColorMedium2())) {
                    qIn.put("colorMedium2", colorMedium2);
                    cc++;
                }
            } else {
                qIn.put("colorMedium2", colorMedium2);
                cc++;
            }
        }
        if (colorLight != null) {
            if (theme.getColorLight() != null) {
                if (!colorLight.equals(theme.getColorLight())) {
                    qIn.put("colorLight", colorLight);
                    cc++;
                }
            } else {
                qIn.put("colorLight", colorLight);
                cc++;
            }
        }
        if (colorLight2 != null) {
            if (theme.getColorLight2() != null) {
                if (!colorLight2.equals(theme.getColorLight2())) {
                    qIn.put("colorLight2", colorLight2);
                    cc++;
                }
            } else {
                qIn.put("colorLight2", colorLight2);
                cc++;
            }
        }
        if (color1 != null) {
            if (theme.getColor1() != null) {
                if (!color1.equals(theme.getColor1())) {
                    qIn.put("color1", color1);
                    cc++;
                }
            } else {
                qIn.put("color1", color1);
                cc++;
            }
        }
        if (color2 != null) {
            if (theme.getColor2() != null) {
                if (!color2.equals(theme.getColor2())) {
                    qIn.put("color2", color2);
                    cc++;
                }
            } else {
                qIn.put("color2", color2);
                cc++;
            }
        }
        if (color3 != null) {
            if (theme.getColor3() != null) {
                if (!color3.equals(theme.getColor3())) {
                    qIn.put("color3", color3);
                    cc++;
                }
            } else {
                qIn.put("color3", color3);
                cc++;
            }
        }
        if (color4 != null) {
            if (theme.getColor4() != null) {
                if (!color4.equals(theme.getColor4())) {
                    qIn.put("color4", color4);
                    cc++;
                }
            } else {
                qIn.put("color4", color4);
                cc++;
            }
        }
        if (status != null) {
            if (theme.getStatus() != null) {
                if (!status.equals(theme.getStatus())) {
                    qIn.put("status", status);
                    cc++;
                    if (status.equals(ESTags.DEFAULT.toString())) {
                        Map qinThemeType = new HashMap();
                        qinThemeType.put("themeType", theme.getThemeType());
                        iThemeMiddle.setAllThemeStatusToActive(qinThemeType);
                    }
                }
            } else {
                qIn.put("status", status);
                cc++;
            }
        }
        if (cc == 0) {
            return;
        }

        qIn.put("themeId", themeId);

        Map qIn2 = new HashMap();
        qIn2.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn2, false);

        iThemeMiddle.updateTheme(qIn);
    }

    @Override
    public Map listAppTheme(Map in) throws Exception {
        String token = in.get("token").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("themeType", ESTags.MOBILE_CLIENT);
        ArrayList<Theme> themes = iThemeMiddle.listTheme(qIn);

        Map out = new HashMap();
        out.put("themeList", themes);

        return out;
    }

    @Override
    public Map getAppTheme(Map in) throws Exception {
        String token = in.get("token").toString();
        String themeId = in.get("themeId").toString();

        Map qIn = new HashMap();
        qIn.put("token", token);
        AdminUserView adminUserView = iAdminUserMiddle.getAdminUser(qIn, false);

        qIn = new HashMap();
        qIn.put("themeId", themeId);
        Theme theme = iThemeMiddle.getTheme(qIn, false);

        Map out = new HashMap();
        out.put("theme", theme);

        return out;
    }


}
