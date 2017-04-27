package com.biz.test;

import com.biz.service.StudentService;
import com.biz.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/27.
 */
@Controller
public class DataController {
    @Autowired
    StudentService studentService;
    @Autowired
    private Jedis jedis;

    @RequestMapping("/add_batch")
    public ModelAndView addBatch() throws ParseException {
        ModelAndView mv = new ModelAndView();
        for (int i = 1; i <= 50; i++) {
            Student student = new Student(i + "", "学生" + i, new Date(), "测试数据" + i, (int) (Math.random() * 100));
            studentService.addStudent(student);
        }
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/flushDB")
    public ModelAndView flushDB() {
        ModelAndView mv = new ModelAndView();
        jedis.flushDB();
        mv.setViewName("index");
        return mv;
    }
}
