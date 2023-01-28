package google.scrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToCSV{
private File file;
private FileWriter myWriter;


public WriteToCSV() {
  file = new File("app/src/main/resources/googleMarket.csv");
  try {
      file.createNewFile();
    myWriter = new FileWriter("app/src/main/resources/googleMarket.csv", false);
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  
}

public void  writeToCSVFile(ArrayList<String> productName, ArrayList<String> companyName, ArrayList<String> cadPrice, ArrayList<String> usdPrice){
  try {
  
    myWriter.write("PRODUCT NAME" + "," + "COMPANY NAME" + "," + "CAD PRICE ($)" + "," + "USD PRICE"  + "\n");
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
    for(int i=0; i<60; i++){
      try {

        myWriter.write(productName.get(i) + "," + companyName.get(i) + "," + cadPrice.get(i) + "," + usdPrice.get(i) + "\n");
      } catch (IOException e) {
        System.out.println("Did not write to CSV file");
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    
    try {
      myWriter.flush();
      myWriter.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println("END");

    
   
   

}

}
