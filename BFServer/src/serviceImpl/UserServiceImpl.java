package serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import service.UserService;

public class UserServiceImpl implements UserService{
    private String userID=null;
	@Override
	public boolean login(String username, String password) throws RemoteException {
		ArrayList<String> userList=new ArrayList<>();
		ArrayList<String> passwords=new ArrayList<>();
		String[] user_info=null;
		try{
			FileReader fr=new FileReader("user_list");
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			while((str=br.readLine())!=null){
				user_info=str.split("/");
				userList.add(user_info[0]);
				passwords.add(user_info[1]);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		if(userList.contains(username)){
			if(passwords.get(userList.indexOf(username)).equals(password)){
				userID=username;
				return true;
				}
			else
				return false;
		}else
			return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		if(userID.equals(username)){
			userID=null;
		    return true;
		    }
		else
			return false;
	}
	
	
}
