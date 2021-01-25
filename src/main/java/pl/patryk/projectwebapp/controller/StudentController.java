package pl.patryk.projectwebapp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import pl.patryk.projectwebapp.model.Student;
import pl.patryk.projectwebapp.service.StudentService;

import javax.validation.Valid;

@Controller
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/studentList")
    public String studentList(Model model, Pageable pageable) {
        model.addAttribute("studenci", studentService.getStudents(pageable).getContent());
        return "studentList";
    }

    @GetMapping("/studentEdit")
    public String studentEdit(@RequestParam(required = false) Integer student_id, Model model) {
        if (student_id != null) {
            model.addAttribute("student", studentService.getStudent(student_id).get());
        } else {
            Student student = new Student();
            model.addAttribute("student", student);
        }
        return "studentEdit";
    }

    @PostMapping(path = "/studentEdit")
    public String studentEditSave(@ModelAttribute @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "studentEdit";
        }
        try {
            student = studentService.setStudent(student);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().getReasonPhrase());
            return "studentEdit";
        }
        return "redirect:/studentList"; // przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
    }

    @PostMapping(params = "cancel", path = "/studentEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/studentList";
    }

    @PostMapping(params = "delete", path = "/studentEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditDelete(@ModelAttribute Student student) {// żądanie będzie zawierało parametr 'delete'
        studentService.deleteStudent(student.getStudentId());
        return "redirect:/studentList";
    }
}