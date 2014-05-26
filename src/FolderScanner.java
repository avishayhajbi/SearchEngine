import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class FolderScanner implements Runnable{
	//SimpleEntry<String, PostingFileElement> posting_file = new SimpleEntry<>(null);
	List <PostingFileElement> postingFile = new ArrayList<>();

	//List<String> filesPathList= new ArrayList<>();
	//List<String,String> words= new ArrayList<>();
	
	@Override
	public void run() {
		while (true){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File folder = new File(System.getProperty("user.dir")+"\\db");
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	
			    	// if its the first file we index
			    	if (postingFile.isEmpty()){
			    		postingFile.add(new PostingFileElement(file.getPath(), postingFile.size() ));
			    		System.out.println("added - " + postingFile.get(postingFile.size()-1).path + "in index: "+postingFile.get(postingFile.size()-1).docNum);
			    		parseFile(file);
			    		
			    	// if it's not the first file we index
			    	}else{
			    		boolean exists = false;
			    		// check if we indexed this file path before
			    		for (int i=0; i<postingFile.size(); i++){
			    			// if file path exist
					    	if (postingFile.get(i).path.equals(file.getPath()) ){
					    		exists=true;
					    		break;
					    	}
			    		}
			    		// if file exist -> Add file to list
			    		if (!exists){
			    			postingFile.add(new PostingFileElement(file.getPath(), postingFile.size() ));
				    		System.out.println("added - " + postingFile.get(postingFile.size()-1).path + "in index: "+postingFile.get(postingFile.size()-1).docNum);
				    		parseFile(file);
			    		}
			    		
			    		
			    		
			    		// check if all path's are still valid
				    	for (int i=0; i<postingFile.size(); i++){
				    		File f = new File(postingFile.get(i).path);
				    		// if not valid -> Remove file from list
				    		if (!f.exists()){
				    			System.out.println("remove - "+postingFile.get(i).path + "in index: "+postingFile.get(i).docNum);
				    			postingFile.remove(i);
				    		}			    		
				    		
				    	}
			    	}
			    }
			}

			
			
		}
	}


	private void parseFile(File file) {
		String everything=null;
		String words[];
		try(BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		everything = everything.replaceAll("[!@#$%^&*\\[\\]\"()-=_+~:;<>?,.{}`|/]","");
		words = everything.split(" "); //  ofir   is student   - ofir , is 
		for(String tmpWord : words){
			tmpWord.trim();
			System.out.println(tmpWord);
		}

	}
}

	

