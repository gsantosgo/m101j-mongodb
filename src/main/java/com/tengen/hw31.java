package com.tengen;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class hw31 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("school");
		DBCollection studentsCollection = db.getCollection("students");
		System.out.println(studentsCollection.count());

		DBCursor cur = studentsCollection.find();
		int count = 0;
		while (cur.hasNext()) {
			DBObject student = cur.next();
			BasicDBList scores = (BasicDBList) student.get("scores");

			int index = 0;
			int posIndex = 0;
			boolean remove = false;
			if (scores != null) {
				count++;
				//System.out.println(count + " " + (String) student.get("name") + scores.size());

				Double minScore = Double.MAX_VALUE;
				//double calculate = 0.0;
				for (Object tempScore : scores) {
					DBObject nested = (DBObject) tempScore;
					String type = (String) nested.get("type");
					Double score = (Double) nested.get("score");
					//System.out.println(" >" + type + ":" + score);
					if (type.equals("homework") && (score < minScore)) {
						remove = true;
						posIndex = index;
						minScore = score;
					}
					index++;
					//calculate += score; 
				}
				//System.out.println((calculate/3));
			}
			// Remove 
			if (remove) {
				System.out.println("Remove " + posIndex);
				BasicDBObject match = new BasicDBObject("_id", student.get("_id"));
				scores.remove(posIndex);
				System.out.println("scores : " + scores);
				BasicDBObject update = new BasicDBObject("scores", scores); 
				studentsCollection.update(match, new BasicDBObject("$set", update));
			}

		}

	}

}
