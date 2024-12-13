package vttp.batch5.ssf.noticeboard.repositories;

import static vttp.batch5.ssf.noticeboard.Util.dateToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.ssf.noticeboard.models.Notice;

@Repository
public class NoticeRepository {

	@Autowired @Qualifier("notice")
	private RedisTemplate<String,Object> template;

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	//randomkey
	public String randomId() {
		return template.randomKey();
	}

	//keys *
	public Set<String> getIds() {
		return template.keys("*");
	}
	
	//hset id(key)
	public void insertNotices(Notice notice) {
		HashOperations<String, String,Object> hashOps = template.opsForHash();

		Map<String, Object> val =  new HashMap<>();
		val.put("title",notice.getTitle().toString());
		val.put("poster", notice.getPoster().toString());
		val.put("postDate", dateToString(notice.getPostDate()));
		val.put("categories", Arrays.toString(notice.getCategories()));

		hashOps.putAll(notice.getId(), val);
	}
}
