//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		char choice=code.charAt(0);
		if(choice=='O'){
			char[] input=param.toCharArray();
			ArrayList<Integer> loops=new ArrayList<>();
			StringBuilder output=new StringBuilder();
			char[] array=new char[100];
			for(int i=0;i<100;i++)
				array[i]=0;
			int index=0;
			int readIndex=0;
			int loopNum=0;
			for(int i=0;i<=code.length()-10;i+=10){
				String comd=code.substring(i, i+10);
				if(comd.equals("Ook. Ook? "))
					index++;
				else if(comd.equals("Ook? Ook. "))
					index--;
				else if(comd.equals("Ook. Ook. "))
					array[index]++;
				else if(comd.equals("Ook! Ook! "))
					array[index]--;
				else if(comd.equals("Ook! Ook. "))
					output.append(array[index]);
				else if(comd.equals("Ook. Ook! ")){
					array[index]=input[readIndex];
					readIndex++;
				}
				else if(comd.equals("Ook! Ook? ")){
					if(array[index]==0){
						loopNum++;
						while(loopNum!=0){
							i+=10;
							comd=code.substring(i, i+10);
							if(comd.equals("Ook! Ook? "))
								loopNum++;
							else if(comd.equals("Ook? Ook! "))
								loopNum--;
						}
					}
					else{
						loops.add(i);
					}
				}
				else if(comd.equals("Ook? Ook! ")){
					if(array[index]==0){
						loops.remove(loops.size()-1);
					}
					else{
						i=loops.get(loops.size()-1);
					}
				}
			}
			return output.toString();
		}
		else{
		char[] codes=code.toCharArray();
		char[] input=param.toCharArray();
		ArrayList<Integer> loops=new ArrayList<>();
		StringBuilder output=new StringBuilder();
		char[] array=new char[100];
		for(int i=0;i<100;i++)
			array[i]=0;
		int index=0;
		int readIndex=0;
		int loopNum=0;
		for(int i=0;i<codes.length;i++){
			if(codes[i]=='>')
				index++;
			else if(codes[i]=='<')
				index--;
			else if(codes[i]=='+')
				array[index]++;
			else if(codes[i]=='-')
				array[index]--;
			else if(codes[i]=='.')
				output.append(array[index]);
			else if(codes[i]==','){
				array[index]=input[readIndex];
				readIndex++;
			}
			else if(codes[i]=='['){
				if(array[index]==0){
					loopNum++;
					while(loopNum!=0){
						i++;
						if(codes[i]=='[')
							loopNum++;
						else if(codes[i]==']')
							loopNum--;
					}
				}
				else{
					loops.add(i);
				}
			}
			else if(codes[i]==']'){
				if(array[index]==0){
					loops.remove(loops.size()-1);
				}
				else{
					i=loops.get(loops.size()-1);
				}
			}
		}
		return output.toString();
		}
		
	}

}
