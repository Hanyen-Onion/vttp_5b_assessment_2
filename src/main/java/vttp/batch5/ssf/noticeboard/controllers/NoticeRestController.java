package vttp.batch5.ssf.noticeboard.controllers;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;


@RestController
@RequestMapping
public class NoticeRestController {

    private static final String URL = "https://publishing-production-d35a.up.railway.app";

    @Autowired
    private NoticeService nSvc;

    @GetMapping(path="/notice", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAPI() {

       try {nSvc.getPayload();

       return ResponseEntity.ok(nSvc.getPayload().toString());
       } catch (Exception ex) {
        System.out.println("from rest GET");
        ex.printStackTrace();
        
       }
       return null;
    }

    //POST /notice
    //content type = app/json
    //accept = app/json
    @PostMapping(path={URL},consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postPayload(@RequestBody MultiValueMap<String,String> form) {

        Notice notice = nSvc.toNotice(form);
        JsonObject json = nSvc.postToNoticeServer(notice);
        System.out.println(json.toString());

        RequestEntity<String> req = RequestEntity
        .post(URL)
        .contentType(MediaType.APPLICATION_JSON)
        .accept( MediaType.APPLICATION_JSON)
        .body(json.toString(), String.class);

        RestTemplate template = new RestTemplate();

        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            System.out.println("sending "+ resp.toString());
            return ResponseEntity.ok(resp.toString());
        } catch (Exception ex) {
            System.out.println("from rest POST");
            ex.printStackTrace();
            
        }
            return null;
    }
    
}
