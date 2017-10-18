package cn.zeffect.fileutils;

public class TextUtils {
	public static boolean isEmpty(String info) {
		return info==null||info.equals("")||info.length()<=0;  
	}
}
