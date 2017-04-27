package com.biz.web.controll;

import com.biz.service.StudentService;
import com.biz.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */
@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    List<Student> studentList;

    @RequestMapping("/list")
    public ModelAndView studentList(int cur_page) throws Exception {
        ModelAndView mv = new ModelAndView();
        studentList = studentService.getOnePage(cur_page);
        mv.addObject("studentList", studentList);
        mv.addObject("total_page", studentService.getTotalPage());
        mv.addObject("cur_page", cur_page);
        mv.setViewName("list");
        return mv;
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAddView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addstudent");
        return mv;
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public ModelAndView addStudent(Student student) throws Exception {
        studentService.addStudent(student);
        return studentList(1);
    }

    @RequestMapping("/toUpdate")
    public ModelAndView toUpdateView(String id, int cur_page) throws ParseException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("student", studentService.getStudentById(id));
        mv.addObject("cur_page", cur_page);
        mv.setViewName("updatestudent");
        return mv;
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public ModelAndView updateStudent(Student student, int cur_page) throws Exception {
        studentService.updateStudent(student);
        return studentList(cur_page);
    }

    @RequestMapping("/delete")
    public ModelAndView deleteStudent(String id, int cur_page) throws Exception {
        studentService.deleteStudent(id);
        return studentList(cur_page);
    }
}
