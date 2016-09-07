package cc.gauto.db.entity;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 20:37
 */
public class ExamEntity {
    public int polygonId;
    public String name;

    public ExamEntity(int id, String name) {
        this.polygonId = id;
        this.name = name;
    }
}
