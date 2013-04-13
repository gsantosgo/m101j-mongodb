package com.tengen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class GridFSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		MongoClient client = new MongoClient(); 
		DB db = client.getDB("course"); 
		FileInputStream inputStream = null;
		
		GridFS videos = new GridFS(db, "videos"); // returns GridFS bucket named "videos" 
		

		try { 
			inputStream = new FileInputStream("video.mp4");
		} catch (FileNotFoundException e) {
			System.out.println("Can't open file");
			System.exit(1); 
		}
		
		GridFSInputFile video = videos.createFile(inputStream, "video.mp4"); 
		
		// create some meta data for the object 
		BasicDBObject meta = new BasicDBObject("description", "Coursera - POSA"); 
		ArrayList<String> tags = new ArrayList<String>(); 
		tags.add("Programming");
		tags.add("Network");
		tags.add("Concurrent");
		meta.append("tags", tags); 
		
		video.setMetaData(meta); 
		video.save(); 
		
		System.out.println("Object ID in Files Collection: " + video.get("_id"));		
		System.out.println("Saved the file to MongoDB");
		
		GridFSDBFile gridFile = videos.findOne(new BasicDBObject("filename", "video.mp4"));
		
		FileOutputStream fileOutputStream = new FileOutputStream("video_copy.mp4");
		gridFile.writeTo(fileOutputStream); 
		
		System.out.println("Write the file back out");
		
	}

}
