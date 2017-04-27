package com.biz.service;

import com.biz.dao.StudentDao;
import com.biz.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/26.
 */
@Service
public class StudentService {
    private static final String StudentScore = "student_score";
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private Jedis jedis;

    /**
     * 增加一个学生
     *
     * @param student
     */
    public int addStudent(Student student) throws ParseException {
        if (!studentDao.isExist(jedis, student.getId())) {
            studentDao.addToList(jedis, student.getId(), student.getAvgscore());
            studentDao.saveStudentToHash(jedis, student);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 删除一个学生
     *
     * @param id
     */
    public void deleteStudent(String id) {
        studentDao.deleteFromList(jedis, id);
        studentDao.deleteStudentHash(jedis, id);
    }

    public Student getStudentById(String id) throws ParseException {
        return studentDao.getStudent(jedis, id);
    }

    /**
     * 修改学生信息
     *
     * @param student
     */
    public void updateStudent(Student student) {
        studentDao.saveStudentToHash(jedis, student);
    }

    /**
     * 获取总页数
     */
    public int getTotalPage() {
        int len = studentDao.getStudentNumber(jedis).intValue();
        if (len % 10 == 0) {
            return len / 10;
        } else {
            return len / 10 + 1;
        }
    }

    /**
     * 获取一页10个学生的信息
     *
     * @return
     */
    public List<Student> getOnePage(int cur_page) throws ParseException {
        List<Student> studentList = new LinkedList<Student>();
        Set<String> id_set = jedis.zrevrange(StudentScore, (cur_page - 1) * 10, cur_page * 10 - 1);
        for (String id : id_set) {
            studentList.add(studentDao.getStudent(jedis, id));
        }
        return studentList;
    }
}
