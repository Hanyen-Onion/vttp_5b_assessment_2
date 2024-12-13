package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;
import static vttp.batch5.ssf.noticeboard.Util.*;


// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService nSvc;

    @GetMapping("/accepted")
    public ModelAndView getAccepted(HttpSession sess) {
        ModelAndView mav = new ModelAndView();

        String id = getNoticeId(sess);


        mav.setViewName("accepted");
        mav.addObject("id", id);

        return mav;
    }

    // validation + submit form
    @PostMapping("/notice")
    public ModelAndView postFormValidate(@Valid @ModelAttribute Notice notice, BindingResult bind, HttpSession sess) {
        ModelAndView mav = new ModelAndView();

        if (bind.hasErrors()) {
            System.out.println("validation error:"+ bind.hasErrors());
            mav.setViewName("notice");
            return mav;
        }
        System.out.println(notice);
        
        String id = nSvc.save(notice);
        notice.setId(id);
        
        System.out.println(id);
       
        sess.setAttribute("id", id);
        
        mav.addObject("notice", notice);
        mav.addObject("id", id);
        System.out.println("got accepted");
        mav.setViewName("accepted");
        
        return mav;
    }

    // landing = notice
    @GetMapping(path={"/", "/notice"})
    public ModelAndView getNotice() {
        
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("notice", new Notice());
        mav.setViewName("notice");

        System.out.println("get notice");
        
        return mav;
    }
}
