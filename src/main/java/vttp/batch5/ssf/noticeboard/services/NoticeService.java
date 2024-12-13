package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.models.Payload;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import static vttp.batch5.ssf.noticeboard.Util.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class NoticeService {

	private static final String URL = "https://publishing-production-d35a.up.railway.app/";

	@Autowired
	private NoticeRepository nRepo;

	public String save(Notice notice) {
		
		String id;
		
		try{ 
			Payload p = getPayload();
			System.out.println(p);
			if (p.getId() != null) {
				id = p.getId();
				notice.setId(id);
				nRepo.insertNotices(notice);
				System.out.println("got id to save");
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
			//continue;
		} finally {

			id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			notice.setId(id);
			nRepo.insertNotices(notice);
			System.out.println("gen new id to save");
			
		}
		return id;
	
	}

	public Payload getPayload() {

		RequestEntity<Void> req = RequestEntity
		.get(URL)
		.accept(MediaType.APPLICATION_JSON)
		.build();

		RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
		
		String payload = resp.getBody();

		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject jsonObj = reader.readObject();
		System.out.println(jsonObj);
		String id = jsonObj.getString("id");
		
		String tStamp = jsonObj.getString("timestamp");
		Date timestamp = stringToDate(tStamp);

		Payload payloadObj = new Payload();
		payloadObj.setId(id);
		payloadObj.setTimeStamp(timestamp);
		
		return payloadObj;
	}

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public JsonObject postToNoticeServer(Notice notice) {
		
		//Build json object
		JsonObject json = Json.createObjectBuilder()
			.add("title", notice.getTitle())
			.add("poster", notice.getPoster())
			.add("postDate", dateToString(notice.getPostDate()))
			.add("categories",Arrays.toString(notice.getCategories()))
			.add("text", notice.getText())
			.build();

		return json;
	}

	public Notice toNotice(MultiValueMap<String,String> form) {
		Notice notice = new Notice();

		notice.setTitle(form.getFirst("title"));
		notice.setPoster(form.getFirst("poster"));
		notice.setPostDate(stringToDate(form.getFirst("postDate")));
		notice.setCategories(form.getFirst("categories").split(","));
		notice.setText(form.getFirst("text"));

		return notice;
	}


}
