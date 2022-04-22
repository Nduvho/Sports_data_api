import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class sportsData {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        //System.out.print("hello");
       Boolean stillRunning = true;

        System.out.println("Welcome to the Sports data app");
        System.out.println("\n");
        System.out.println("This app allows you to view the leagues, countries and teams");
        System.out.println("\n");



      do{

          HashMap<Integer, String> datatype = new HashMap<Integer, String>();

          datatype.put(1,"league_id");
          datatype.put(2,"country_id");
          datatype.put(3,"name");

          
          Scanner sc =new Scanner(System.in);

          int data=0;

          String data_id;
          String league, country, team;


//          HttpRequest(league,country,team);

          System.out.println("To view different types of data enter 1.(leagues) \t 2.(countries) \t  3.(teams) ");
          data =sc.nextInt();

          while(data<1|| data>3){
              System.out.println("Please specify a number 1-3 to choose what data you want to display ");
              System.out.println("To view data type in 1.(leagues) \t 2.(countries) \t  3.(teams) ");
              data = sc.nextInt();
          }




          if (data==1)  {
              System.out.println("Please enter the league you want to view: ");
              league = sc.next();
              
              leagueRequest();

          }

          else if(data==2) {
              System.out.println("Please enter the country you want to view: ");
              country = sc.next();
              countryRequest();
          }
          else if(data==3) {
              System.out.println("Please enter the team you want to view: ");
              team = sc.next();
              teamsRequest();
          }
          System.out.println("Would you like to check more data on the sports app");
          System.out.println("1.(Yes) \t Or any other integer to close the program");
          if(sc.nextInt() != 1){
              stillRunning = false;
          }


      }while(stillRunning);

       System.out.println("Thank you. You've successfully completed the program");
       System.out.println("\n");
       System.out.println("You may close the session");

    }

    private static void HttpRequest(String league_id, String country_id, String name ) throws IOException
    {
       String pattern ="##.##";
        DecimalFormat f = new DecimalFormat(pattern);

        try{
            String apikey = "1f8177a0-ba72-11ec-b83e-09e34675ae35";
      
           String url = "https://app.sportdataapi.com/api/v1/soccer/leagues/314?apikey=1f8177a0-ba72-11ec-b83e-09e34675ae35";



            URL urlForGetRequest = new URL(url);

            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                //System.out.println(response.toString());
                System.out.println("\n");
                JSONObject object = new JSONObject(response.toString());
                int data = object.getJSONObject("data").getInt(league_id);
                int data2 = object.getJSONObject("data").getInt(country_id);


                System.out.println(object.getJSONObject("data"));
                System.out.println(data); //keep for debugging
                System.out.println(data2);
                System.out.println("\n");
                //System.out.println(f.format());

            } else {
                throw new Exception("Error in API Call");
            }



        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void leagueRequest() throws IOException{
        String league_id, country_id, name;
        String pattern ="##.##";
        DecimalFormat f = new DecimalFormat(pattern);
        
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://app.sportdataapi.com/api/v1/soccer/leagues?apikey=1f8177a0-ba72-11ec-b83e-09e34675ae35")
                .get()
                .addHeader("apikey", "1f8177a0-ba72-11ec-b83e-09e34675ae35")
                .addHeader("sportsdata-host","app.sportdataapi.com")
                .build();

        try{
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) throw new IOException("Unexpected code : " + response);
            Headers responseHeader = response.headers();
            for (int i =0; i< responseHeader.size(); i++) {
                System.out.println(responseHeader.name(i) + ":" + responseHeader.value(i));
            }
            System.out.println(response.body().string());
            System.out.println(response);
            System.out.println(response.code());
            System.out.println("");

        }  catch (IOException e)  {
             e.printStackTrace();
        }
    }



    private static void countryRequest() throws IOException{

      String pattern ="##.##";
      DecimalFormat f = new DecimalFormat(pattern);

      OkHttpClient client = new OkHttpClient();

      Request request = new Request.Builder()
              .url("https://app.sportdataapi.com/api/v1/soccer/countries?apikey=1f8177a0-ba72-11ec-b83e-09e34675ae35&continent")
              .get()
              .addHeader("apikey", "1f8177a0-ba72-11ec-b83e-09e34675ae35")
              .addHeader("sportsdata-host","app.sportdataapi.com")
              .build();

             try{

                 Response response = client.newCall(request).execute();
                 if(!response.isSuccessful()) throw new IOException("Unexpected code : " + response);
                 Headers responseHeader = response.headers();

                 for (int i =0; i< responseHeader.size(); i++) {
                      System.out.println(responseHeader.name(i) + ":" + responseHeader.value(i));
                  }

                 System.out.println(response.body().string());
                 System.out.println(response);                
                 System.out.println(response.code());         
                 System.out.println("");                      



             } catch (IOException e){

                 e.printStackTrace();

             }


    }


    private static void teamsRequest() throws IOException{

         String pattern ="##.##";
         //JSONObject name;
         DecimalFormat f = new DecimalFormat(pattern);

         OkHttpClient client = new OkHttpClient();

         Request request = new Request.Builder()
                 .url("https://app.sportdataapi.com/api/v1/soccer/teams?apikey=1f8177a0-ba72-11ec-b83e-09e34675ae35&country_id=48")
                 .get()
                 .addHeader("apikey", "1f8177a0-ba72-11ec-b83e-09e34675ae35")
                 .addHeader("sportsdata-host","app.sportdataapi.com")
                 .build();

         
                try{
                    Response response = client.newCall(request).execute();
                    if(!response.isSuccessful()) throw new IOException("Unexpected code : " + response);
                    Headers responseHeader = response.headers();

                    for (int i =0; i< responseHeader.size(); i++) {
                         System.out.println(responseHeader.name(i) + ":" + responseHeader.value(i));
                     }




                    System.out.println(response.body().string());

                    System.out.println(response);
                    System.out.println(response.code());
                    System.out.println("");



                } catch (IOException e){

                   e.printStackTrace();

                }
    }

}
