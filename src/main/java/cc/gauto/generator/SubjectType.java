package cc.gauto.generator;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-10 21:51
 */
public enum SubjectType {
    Unkown(-1),
    Daoku(1),
    Cefang(2),
    Poqi(3),
    Zhijiao(4),
    Quxian(5),
    Youzhijiao(8);

    protected int value;

    SubjectType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static SubjectType fromInt(int i) {
        for (SubjectType b : SubjectType .values()) {
            if (b.value() == i) {
                return b;
            }
        }
        return Unkown;
    }
}
