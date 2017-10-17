package cn.zeffect.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileInfo {
	public static void main(String[] args) {
		log("该类用于输出某个目录的文件列表信息");
		List<File> files=getFileInfo("D:\\BaiduYunDownload");
		
	}
	
	
	
	private static String makeJson(List<File> files) {
		if (files==null||files.isEmpty()) {
			return "";
		}
		JSONArray dataArray=new JSONArray();
		for (int i = 0; i < files.size(); i++) {
			JSONObject dataJson=new JSONObject();
			try {
				File tempFile=files.get(i);
				dataJson.put("name", tempFile.getName());
				dataJson.put("md5", "");
				dataJson.put("lastmodified", tempFile.lastModified());
				dataJson.put("length", tempFile.length());
				dataJson.put("localpath", tempFile.getAbsolutePath());
			} catch (JSONException e) {
			}finally {
				dataArray.put(dataJson);
			}
		}
		return dataArray.toString();
	}
	
	/**
	 * 
	 * @param path 一定要是一个目录
	 */
	private static List<File> getFileInfo(String path) {
		if (isEmpty(path)) {
			return Collections.EMPTY_LIST;
		}
		File tempFile=new File(path);
		if (!tempFile.exists()) {
			return Collections.EMPTY_LIST;
		}
		List<File> files=new ArrayList<>();
		if (tempFile.isDirectory()) {
			for (int i = 0; i < tempFile.listFiles().length; i++) {
				files.addAll(getFileInfo(tempFile.listFiles()[i].getAbsolutePath()));
			}
		}else {
			files.add(tempFile);
		}
		return files;
	}
	

	private static boolean isEmpty(String info) {
		return info==null||info.equals("")||info.length()<=0;  
	}

	private static void log(String info) {
		System.out.println(info);
	}
}
