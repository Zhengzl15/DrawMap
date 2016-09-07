package cc.gauto.db.entity;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 20:32
 */
public class SubjectEntity {
    public int type;
    public int polygonId;

    public SubjectEntity(int t, int id) {
        this.type = t;
        this.polygonId = id;
    }
}
