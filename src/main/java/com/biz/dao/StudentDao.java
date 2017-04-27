package com.biz.dao;

import com.biz.vo.Student;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/26.
 */
@Repository("studentDao")
public class StudentDao {
    private static final String StudentScore = "student_score";

    /**
     * 生成学生信息hash表
     *
     * @param jedis
     * @param student
     */
    public void saveStudentToHash(Jedis jedis, Student student) {
        String hashKey = getHashKey(student.getId());
        Map student_info = new HashMap();
        student_info.put("id", student.getId());
        student_info.put("name", student.getName());
        student_info.put("birthday", new SimpleDateFormat("yyyy-MM-dd").format(student.getBirthday()));
        student_info.put("description", student.getDescription());
        student_info.put("avgscore", String.valueOf(student.getAvgscore()));
        jedis.hmset(hashKey, student_info);
    }

    /**
     * 删除一个学生hash表
     *
     * @param id
     * @return
     */
    public Long deleteStudentHash(Jedis jedis, String id) {
        return jedis.del(getHashKey(id));
    }

    /**
     * 向有序表中增加一个学生的学号和分数
     *
     * @param jedis
     * @param id
     * @param score
     * @return
     */
    public Long addToList(Jedis jedis, String id, int score) {
        return jedis.zadd(StudentScore, score, id);
    }

    /**
     * 从list中删除学生id和分数
     *
     * @param jedis
     * @param id
     * @return
     */
    public Long deleteFromList(Jedis jedis, String id) {
        return jedis.zrem(StudentScore, id);
    }

    public Long getStudentNumber(Jedis jedis) {
        return jedis.zcard(StudentScore);
    }

    /**
     * 通过id获取student
     *
     * @param jedis
     * @param id
     * @return
     * @throws ParseException
     */
    public Student getStudent(Jedis jedis, String id) throws ParseException {
        Map info = jedis.hgetAll(getHashKey(id));
        Student student = new Student();
        student.setId(info.get("id").toString());
        student.setName(info.get("name").toString());
        student.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse((String) info.get("birthday")));
        student.setDescription(info.get("description").toString());
        student.setAvgscore(Integer.parseInt(info.get("avgscore").toString()));
        return student;
    }

    /**
     * 查询学生是否已存在
     */
    public Boolean isExist(Jedis jedis, String id) {
        return jedis.exists(getHashKey(id));
    }

    /**
     * 生成hashKey
     *
     * @param id
     * @return
     */
    private String getHashKey(String id) {
        return "student_info_" + id;
    }
}
