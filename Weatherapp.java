import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class Weatherapp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        scanner.close();

        try {
            String apiKey = "88b49f048d396b4c4f06fdff37d631eb"; // 🔑 Replace with your API key
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey
                    + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            JSONObject obj = new JSONObject(json.toString());

            String cityName = obj.getString("name");
            double temperature = obj.getJSONObject("main").getDouble("temp");
            int humidity = obj.getJSONObject("main").getInt("humidity");
            String weatherDescription = obj.getJSONArray("weather").getJSONObject(0).getString("description");

            System.out.println("\n=== Current Weather in " + cityName + " ===");
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Humidity   : " + humidity + "%");
            System.out.println("Condition  : " + weatherDescription);

        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
    }
}
