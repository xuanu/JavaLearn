package cn.zeffect.fileutils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileInfo {
	public static final String SERVER_IP="http://192.168.1.148:8888/";
	public static final String SEACHER_PATH="E:\\��ʱ�����ļ�";
	public static final String SAVE_PATH="E:\\wytinfo.txt";
	
	public static void main(String[] args) {
		log("�����������ĳ��Ŀ¼���ļ��б���Ϣ");
		List<File> files=getFileInfo(SEACHER_PATH);
		FileCompare.write(SAVE_PATH, makeJson(files));
		log("������");
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
				dataJson.put("downurl", SERVER_IP+tempFile.getAbsolutePath().replace(SEACHER_PATH, ""));
			} catch (JSONException e) {
			}finally {
				dataArray.put(dataJson);
			}
		}
		return dataArray.toString();
	}
	
	/**
	 * 
	 * @param path һ��Ҫ��һ��Ŀ¼
	 */
	private static List<File> getFileInfo(String path) {
		if (TextUtils.isEmpty(path)) {
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
	

	

	private static void log(String info) {
		System.out.println(info);
	}
}
