package bookmark_spring_boot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class IOUtil {
	
	public static void read(ArrayList<String> data,String fileName) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))){
			
			String line;
			while((line = br.readLine())!=null) {
				data.add(line);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String read(InputStream in) {
		
		StringBuilder text = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
			
			String line;
			while((line = br.readLine())!=null) {
				text.append(line).append("\n");	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return text.toString();
	}

	public static void write(String webpage,long id) {
		
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("pages/"+String.valueOf(id)+".html")))){
			
			writer.write(webpage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
