package com.dx.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class unZipAndindexDocs {
	public static void main(String[] args) {
		String str="d:\123\123\20160504162452692582490.xml";

	}

	/**
	 * 解压zip格式的压缩包
	 * 
	 * @param filePath
	 *            压缩文件路径
	 * @param outPath
	 *            输出路径
	 * @return 解压成功或失败标志
	 */
	public static Boolean unZip(String filePath, String outPath) {
		String unzipfile = filePath; // 解压缩的文件名
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(unzipfile));
			ZipEntry entry;
			// 创建文件夹
			while ((entry = zin.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					File directory = new File(outPath, entry.getName());
					if (!directory.exists()) {
						if (!directory.mkdirs()) {
							System.exit(0);
						}
					}
					zin.closeEntry();
				} else {
					File myFile = new File(entry.getName());
					FileOutputStream fout = new FileOutputStream(outPath + myFile.getPath());
					DataOutputStream dout = new DataOutputStream(fout);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = zin.read(b)) != -1) {
						dout.write(b, 0, len);
					}
					dout.close();
					fout.close();
					zin.closeEntry();
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	// 获取某目录中指定类型的文件

	static String indexDocs(File file) throws Exception {
	String filepath=null;
	if (file.isDirectory()) {// 是不是目录
		String[] files = file.list();// 返回该目录下所有文件及文件夹数组
		Arrays.sort(files); // 排序
		for(int i=0;i<files.length;i++)
			if(files[i].indexOf(".zip")>0){
				filepath=(file.getParent() + "/" + files[i]);// 目录/文件名
			}
		}
	return filepath;
	}
}
