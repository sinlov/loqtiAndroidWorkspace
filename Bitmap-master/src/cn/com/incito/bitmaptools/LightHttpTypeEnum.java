
package cn.com.incito.bitmaptools;

public enum LightHttpTypeEnum {
    NULL(LightHttpEnum.NULL),
    TYEP_DEFALUT(LightHttpEnum.TYEP_DEFALUT),
    TYPE_JSON_GET(LightHttpEnum.TYPE_JSON_GET),
    TYPE_JSON_POST(LightHttpEnum.TYPE_JSON_POST),
    TYPE_BITMAP_DOWN(LightHttpEnum.TYPE_BITMAP_DOWN),
    TYPE_BITMAP_UPLOAD(LightHttpEnum.TYPE_BITMAP_UPLOAD)
    ;
    private final LightHttpEnum value;

    private LightHttpTypeEnum(LightHttpEnum value) {
        this.value = value;
    }
    public static LightHttpTypeEnum getType (int code){
        LightHttpEnum type = LightHttpEnum.fromInt(code);
        if (type == null) {
            return LightHttpTypeEnum.NULL;
        }
        for (LightHttpTypeEnum lty : LightHttpTypeEnum.values()) {
            if (lty.value == type) {
                return lty;
            }
        }
        return LightHttpTypeEnum.NULL;
    }
}
