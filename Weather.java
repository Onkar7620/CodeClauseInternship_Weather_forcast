package Wheather_app;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;

public class Wheather_app {
    private static JFrame frame;
    private static JTextField locationField;
    private static JTextArea weatherDisplay;
    private static JButton fetchButton;
    private static String apiKey="5de8fa21421733f721bd1bf9e06de20a";
    private static String fetchWeatherData(String city){
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apiKey);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response="";
            String line;
            while((line=reader.readLine())!=null){
                response+=line;
            }
            reader.close();
            JSONObject jsonObject=(JSONObject) JSONValue.parse(response.toString());
            JSONObject mainObj=(JSONObject) jsonObject.get("main");
            double temperatureKelvin=(double)mainObj.get("temp");
            long humidity=(long)mainObj.get("humidity");
            double temperatureCelcius=temperatureKelvin-273.15;
            JSONArray weatherArray=(JSONArray) jsonObject.get("weather");
            JSONObject weather=(JSONObject) weatherArray.get(0);
            String description=(String) weather.get("description");
            return "Description : "+description + "\nTemperature : "+temperatureCelcius+ " celcius"+"\nhumidity:"+humidity+"%";

        }catch (Exception e){
            return "Failed to find weather data. please check you city and api key..";
        }
    }
    public static void main(String[] args) {
        frame=new JFrame("Wheather forcast App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        locationField=new JTextField(15);
        fetchButton =new JButton("Find Wheather");
        weatherDisplay=new JTextArea(10,30);
        weatherDisplay.setEditable(false);
        frame.add(new JLabel("Enter city Name"));
        frame.add(locationField);
        frame.add(fetchButton);
        frame.add(weatherDisplay);
        frame.setVisible(true);
        fetchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String city=locationField.getText();
                String weatherInfo=fetchWeatherData(city);
                weatherDisplay.setText(weatherInfo);
            }
        });
    }
}
