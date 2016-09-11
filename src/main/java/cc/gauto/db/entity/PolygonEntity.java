package cc.gauto.db.entity;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 20:57
 */
public class PolygonEntity {
    public long polygonId;
    public int layer;
    public int vertexNum;
    public double[] verteces;

    public PolygonEntity(long id, int layer, int vertexNum, double[] verteces) {
        this.polygonId = id;
        this.layer = layer;
        this.vertexNum = vertexNum;
        this.verteces = verteces;
    }
}
