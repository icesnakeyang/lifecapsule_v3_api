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
        if (status != null) {
            if (theme.getStatus() != null) {
                if (!status.equals(theme.getStatus())) {
                    qIn.put("status", status);
                    cc++;
                    if(status.equals(ESTags.DEFAULT.toString())){
                        Map qinThemeType=new HashMap();
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
