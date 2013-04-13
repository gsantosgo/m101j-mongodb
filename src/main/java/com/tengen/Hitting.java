package com.tengen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class Hitting {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		MongoClient client = new MongoClient(); 
		DB db = client.getDB("test");
		
		BasicDBObject query = new BasicDBObject("a", 40000);
		query.append("b", 40000); 
		query.append("c", 40000);
		
		DBCollection c = db.getCollection("foo"); 
		
		DBObject doc = c.find(query).hint("a_1_b_-1_c_1").explain(); 
		
		//Alternative
		//BasicDBObject myHint = new BasicDBObject("a", 1).append("b", -1).append("c", 1); 
		//DBObject doc = c.find(query).hint(myHint).explain();

		
		for (String s: doc.keySet()) {
			System.out.printf("%25s:%s\n", s, doc.get(s));
		}
	}

}
