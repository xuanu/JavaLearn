package cn.zeffect.fileutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * 
 * @author cwj
 *
 */
public class FileCompare {
	
	
	/****
     * 读取文件内容
     * @param filePath 路径
     */
    public static String read(String filePath){
        if (TextUtils.isEmpty(filePath)) return "";
        File tempFile =new File(filePath);
        if (!tempFile.exists() || tempFile.isDirectory()) return "";
        FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(tempFile);
			 return inputStream2String(fileInput);
		} catch (FileNotFoundException e) {
			return "";
		}
      
    }
    
    public static boolean write(String filePath, String content) {
    	return write(filePath, content,false);
    }

    /***
     * 写文件内容
     * @param filePath 路径
     * @param content 内容
     * @param append 是否追加
     */
    public static boolean write(String filePath, String content, boolean append) {
        try {
            if (TextUtils.isEmpty(filePath)) return false;
            File tempFile =new File(filePath);
            if (!tempFile.exists() || tempFile.isDirectory()) return false;
            FileWriter writer = new FileWriter(filePath, append);
            writer.write(content);
            writer.close();
            return true;
        } catch ( IOException e) {
            return false;
        }


    }
    
    private static String inputStream2String(InputStream inputs) {
    	ByteArrayOutputStream baos =new ByteArrayOutputStream();
        try {
            int i = inputs.read();
            while (i != -1) {
                baos.write(i);
                i = inputs.read();
            }
        } catch (IOException e) {
        } finally {
            return baos.toString();
        }
    }

	/**
	 * 计算文件的 MD5 值，用于比较两个文件是否相同。
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return getFileMD5(in);
	}

	/**
	 * 计算文件的 MD5 值，用于比较两个文件是否相同。
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(InputStream in) {
		if (in == null) {
			return null;
		}
		MessageDigest digest = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			return byteArrayToHexString(digest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
