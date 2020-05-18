package com.example.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

@Component
public class CouchbaseImp {
	private Cluster cluster = null;
	private Bucket bucket = null;
	private String bucketName="Intern";
	private Collection collection;
	
	public void setBucket() {
		cluster = Cluster.connect("127.0.0.1", "Admin", "admin123@");
		bucket = cluster.bucket(bucketName);
		collection = bucket.defaultCollection();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public MutationResult upsert(String id, Object object) {
		System.out.println(object.toString());
		return collection.upsert(id, object);
	}


	public MutationResult remove(String id) {
		System.out.println("Removing document " + id);
		return collection.remove(id);
	}


	public List<String> query(String query, JsonObject jo) {
		List<String> data = new ArrayList<String>();
		QueryResult result = cluster.query("select * from `" + bucketName + "`" + query,
				QueryOptions.queryOptions().parameters(jo));
		for (JsonObject row : result.rowsAsObject()) {
			System.out.println("Found row: " + row.get(bucketName).toString());
			data.add(row.get(bucketName).toString());
		}
		return data;
	}


	public void disconnect() {
		if (cluster != null) {
			cluster.disconnect();
		}
	}

}
