package cc.gauto.db;

import cc.gauto.db.entity.ExamEntity;
import cc.gauto.db.entity.PolygonEntity;
import cc.gauto.db.entity.RoadEntity;
import cc.gauto.db.entity.SubjectEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-07-10 21:28
 */
public class DBHelper {
    private static DBHelper mDBHelper = null;
    private String dbUrl;
    private Connection connection = null;

    public static DBHelper getInstance(String dbUrl) {
        if (mDBHelper != null) {
            mDBHelper.closeConnection();
        }
        try {
            mDBHelper = new DBHelper(dbUrl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mDBHelper;
    }

    public DBHelper(String url) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.dbUrl = url;
        initConnection();
    }

    private void initConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ResultSet executeSql(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        //statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    //获取数据库中所有的考场
    public List<ExamEntity> getExam() {
        List<ExamEntity> exams = new ArrayList<>();
        try {
            String sql = "select polygon_Id, name from EXAM";
            ResultSet rs = executeSql(sql);
            while (rs.next()) {
                int polygonId = rs.getInt("polygon_Id");
                String name = rs.getString("name");
                exams.add(new ExamEntity(polygonId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exams;
    }

    //根据考场id获取该考场里所有Subject的id
    public List<SubjectEntity> getSubjectByExamId(int examId) {
        List<SubjectEntity> subjects = new ArrayList<>();
        try {
            String sql = "select polygon_Id,type from SUBJECT where parent_Id=" + examId;
            ResultSet rs = executeSql(sql);
            while (rs.next()) {
                int polygonId = rs.getInt("polygon_Id");
                int type = rs.getInt("type");
                subjects.add(new SubjectEntity(type, polygonId));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    //根据subject id 获取该subject下的road id
    public List<RoadEntity> getRoadBySubjectId(int subjectId) {
        List<RoadEntity> roads = new ArrayList<>();
        try {
            String sql = "select polygon_Id from ROAD where parent_Id=" + subjectId;
            ResultSet rs = executeSql(sql);
            while (rs.next()) {
                long polygonId = rs.getLong("polygon_Id");
                roads.add(new RoadEntity(polygonId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roads;
    }

    //根据polygon id获取Polygon
    public PolygonEntity getPolygonById(long id) {
        PolygonEntity polygonEntity = null;
        try {
            String sql = "select * from POLYGON where _id=" + id;
            ResultSet rs = executeSql(sql);
            while (rs.next()) {
                int layer = rs.getInt("layer");
                int vertexNum = rs.getInt("vertex_Num");
                String verticesStr = rs.getString("vertices");
                //将字符串的点分割为浮点
                double[] vertices = new double[vertexNum*2];
                if (verticesStr != null) {
                    verticesStr  = verticesStr.trim();
                    String[] splitStr = verticesStr.split(";");
                    int index = 0;
                    for (String tmpStr : splitStr) {
                        if (tmpStr.equals(";")) {
                            continue;
                        }
                        tmpStr = tmpStr.trim();
                        String[] tmpStrSplit = tmpStr.split(",");
                        if (tmpStrSplit.length == 2) {
                            //正常情况
                            double x = Double.parseDouble(tmpStrSplit[0]);
                            double y = Double.parseDouble(tmpStrSplit[1]);
                            vertices[index] = x;
                            vertices[index + 1] = y;
                            index = index + 2;
                        }
                    }
                }
                polygonEntity = new PolygonEntity(id, layer, vertexNum, vertices);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return polygonEntity;
    }
}
