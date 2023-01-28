package google.scrapper;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainBot implements Runnable{
    private Thread t;
    private String url;
    private Document document;
    private ArrayList<String> productNames;
    private ArrayList<String> price;
    private ArrayList<String> company;
    private ArrayList<String> tempMoneyConversion;
    private ArrayList<Float> moneyConversion;
    private ArrayList<Float> yenPriceConversion;
    private ArrayList<String> USDPrice;

    final String currencyURL = "https://www.google.com/search?q=currency+exchange&rlz=1C1VDKB_enCA1034CA1034&oq=currency+exchange+&aqs=chrome..69i57j0i512l9.2576j1j7&sourceid=chrome&ie=UTF-8";
    private Document currencyDoc;
    private float cadDollar;
    private float yenDollar;
    WriteToCSV writeToCSV;



    MainBot(String url){
        this.url = url;
        productNames = new ArrayList<>();
        price = new ArrayList<>();
        tempMoneyConversion = new ArrayList<>();
        moneyConversion = new ArrayList<>();
        company = new ArrayList<>();
        yenPriceConversion = new ArrayList<>();
        USDPrice = new ArrayList<>();
        writeToCSV = new WriteToCSV();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Cannot scan items");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Elements itemNames = document.body().getElementsByClass("tAxDx");

        for(Element itemName : itemNames){
            productNames.add(itemName.text().replace(",", ""));
            
        }

        Elements itemPrices = document.body().getElementsByClass("a8Pemb OFFNJ");

        for(Element itemPrice : itemPrices){
            price.add(itemPrice.text().replace(",", ""));
        
        }

        Elements companyNames = document.body().getElementsByClass("aULzUe IuHnof");

        for(Element companyName : companyNames){
            company.add(companyName.text().replace(",", ""));
        }
 
        
        for(int i=0; i<price.size();i++){
            tempMoneyConversion.add(price.get(i).replace(",", "").replace("CA$", ""));
            moneyConversion.add(Float.parseFloat(tempMoneyConversion.get(i)));

            yenPriceConversion.add(moneyConversion.get(i) / currencyExchange());
     
            USDPrice.add("$"+String.format("%.2f", yenPriceConversion.get(i)));
            System.out.println(USDPrice.get(i));
        }

        writeToCSV();

        
        
    }

    public void start () {
        System.out.println("Starting " );
        if (t == null) {
           t = new Thread(this);
           t.start();
        }
}

    public float currencyExchange(){
        try {
            currencyDoc = Jsoup.connect(currencyURL).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Elements canadianDollar = currencyDoc.body().getElementsByClass("DFlfde eNFL1");

        
        String CAD = canadianDollar.text();

        try {
            cadDollar = Float.parseFloat(CAD);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }

        Elements jpDollar = currencyDoc.body().getElementsByClass("DFlfde SwHCTb");
        String yen = jpDollar.text();

        try {
            yenDollar = Float.parseFloat(yen);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }

        float moneyDiff = cadDollar / yenDollar;
        
        return moneyDiff;


    }

    public void writeToCSV(){
       
        writeToCSV.writeToCSVFile(productNames, company, price, USDPrice);
       
        
    }
}
