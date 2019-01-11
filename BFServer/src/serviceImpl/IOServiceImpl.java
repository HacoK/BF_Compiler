package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File list = new File(userId);
		if(!list.exists()){
			list.mkdirs();
		}
		File f = new File(list,userId + "_" + fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		StringBuilder code=new StringBuilder();
		try{
			FileReader fr=new FileReader("D:\\软工\\大作业\\BFServer\\"+userId+"\\"+userId+"_"+fileName);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				code.append(str);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	
		return code.toString();
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		StringBuilder files=new StringBuilder();
		  File file=new File(userId);
		  File[] tempList = file.listFiles();
		  for (int i = 0; i < tempList.length; i++) {
			  files.append(tempList[i]+"\n");
		  }
		return files.toString();
	}
	
	@Override
	public String getUserList() {
		// TODO Auto-generated method stub
		StringBuilder info=new StringBuilder();
		try{
			FileReader fr=new FileReader("user_list");
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				info.append(str);
				info.append("/n");
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	
		return info.toString();
	}
	@Override
	public void writeUserList(String content){
		try{
			FileWriter fw=new FileWriter("user_list",true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
