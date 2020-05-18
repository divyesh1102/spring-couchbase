package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.json.JsonObject;
import com.google.gson.Gson;

@RestController
public class GetDataController {
	String bucket = "Intern";
	@Autowired
	private CouchbaseImp couchbaseImp;
	
	@PostMapping("/")
	public void show(Intern intern) {
		couchbaseImp.setBucket();
		Gson gson = new Gson();
		String data = gson.toJson(intern);
		couchbaseImp.upsert(String.valueOf(intern.getId()),JsonObject.fromJson(data));
		couchbaseImp.disconnect();
	}
	
	@DeleteMapping("/{id}")
	public void remove(@PathVariable Integer id) {
		couchbaseImp.setBucket();
		couchbaseImp.remove(id.toString());
		couchbaseImp.disconnect();
	}
	
	@GetMapping(value = "/id/{id}",produces = "application/json")
	@ResponseBody
	public String show(@PathVariable Integer id) {
		couchbaseImp.setBucket();
		List<String> list = couchbaseImp.query("where `id` = "+id,JsonObject.create().put("bucket", bucket));
		couchbaseImp.disconnect();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String data = null;
		try {
			data = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return data; 
	}
	
	@GetMapping(value="/tech/{tech}",produces = "application/json")
	@ResponseBody
	public List<String> showBasedOnTech(@PathVariable String tech) {
		couchbaseImp.setBucket();
		List<String> list = couchbaseImp.query("where `tech` = '"+tech+"'",JsonObject.create().put("bucket", bucket));
		couchbaseImp.disconnect();
		return list;
	}
}
