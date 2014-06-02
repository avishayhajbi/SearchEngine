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

	
	/*->(update after files change)->*/ List <PostingFileElement> postingFile = new ArrayList<>();
	/*->(parse)->*/ List <LocationOrderElement> LocationOrder = new ArrayList<>();
	/*->(sort)->*/ List <AlphabeticOrderElement> AlphabeticOrder = new ArrayList<>();
	/*->(weight)->*/ List <DB> db = new ArrayList<>();

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
			    	
			    	// if it's the first file we index
			    	if (postingFile.isEmpty()){
			    		postingFile.add(new PostingFileElement(file.getPath(), postingFile.size() ));
			    		System.out.println("added - " + postingFile.get(postingFile.size()-1).path + "in index: "+postingFile.get(postingFile.size()-1).docNum);
			    		parseFile(file, postingFile.size()-1);
			    		
			    	// if it's not the first file we index
			    	}else{
			    		boolean exists = false;
			    		// check if we indexed this file-path before
			    		for (int i=0; i<postingFile.size(); i++){
			    			// if file path exist
					    	if (postingFile.get(i).path.equals(file.getPath()) ){
					    		exists=true;
					    		break;
					    	}
			    		}
			    		// if file-path don't exist -> Add file to list
			    		if (!exists){
			    			postingFile.add(new PostingFileElement(file.getPath(), postingFile.size() ));
				    		System.out.println("added - " + postingFile.get(postingFile.size()-1).path + "in index: "+postingFile.get(postingFile.size()-1).docNum);
				    		
				    		//for create 'LocationOrder' list
				    		parseFile(file, postingFile.size()-1);
				    		
				    		//for create 'AlphabeticOrder' list
				    		sort();
				    		
				    		//for create 'db'
				    		weight();
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


	


	





	private void parseFile(File file, int fileIndex) {
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
		
		everything = everything.replaceAll("[!@#$%^&*\\[\\]\"()/\\-=_+~:;<>?,.{}`|/]","");
		words = everything.split(" "); //  ofir   is student   - ofir , is 
		
		// delete all spaces and put in 'LocationOrder' list
		for(String tmpWord : words){
			tmpWord.trim();
			if (!tmpWord.equals("") && !tmpWord.equals("\\")){
				LocationOrder.add(new LocationOrderElement(tmpWord, fileIndex));
				System.out.println("LocationOrder= "+tmpWord);
			}
		}
	}
	
	private void sort() {
		// TODO Auto-generated method stub
		
	}
	
	private void weight() {
		// TODO Auto-generated method stub
		
	}
}

	

