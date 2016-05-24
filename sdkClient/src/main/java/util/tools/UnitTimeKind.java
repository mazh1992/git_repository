package util.tools;

/**
 *
 * <p>时间单位 m-分钟，h-小时，d-天</p>
 * @author leelun
 * @version $Id: UnitTimeKind.java, v 0.1 2014-5-8 下午2:34:20 lilun Exp $
 */
public enum UnitTimeKind {

    MINUTE("m"),
    HOUR("h"),
    DAY("d"),

    ;

    private String code;

    private UnitTimeKind(String code) {
        this.code = code;
    }

    public static UnitTimeKind getByCode(String code) {
        for (UnitTimeKind ls : UnitTimeKind.values()) {
            if (ls.code.equals(code)) {
                return ls;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(String code) {
        return getCode().equals(code);
    }
}
