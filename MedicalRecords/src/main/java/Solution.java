import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Solution {
    public static int[] getRecordsDiagnosed(int doctorId, int breathingRate, int diagnosisId) {
        List<JSONObject> filteredRecords = new ArrayList<JSONObject>();
        int page = 1;
        int totalPages = 1;

        try {
            while (page <= totalPages) {
            	//Create URL
                String urlString = "https://jsonmock.hackerrank.com/api/medical_records?page=" + page;
                URI uri = new URI(urlString); 
                URL url = uri.toURL(); 
                
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                
                if (responseCode == HttpURLConnection.HTTP_OK) 
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                   
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) 
                    {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    
                    if (page == 1) {
                        totalPages = jsonResponse.getInt("total_pages");
                    }
                    
                    JSONArray data = jsonResponse.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject record = data.getJSONObject(i);
                        JSONObject doctor = record.getJSONObject("doctor");
                        JSONObject diagnosis = record.getJSONObject("diagnosis");
                        int docId = doctor.getInt("id");
                        int diagId = diagnosis.getInt("id");
                        if (docId == doctorId && diagId == diagnosisId) {
                            filteredRecords.add(record);
                        }
                    }
                } 
                else {
                    System.out.println("Request Failed: "+responseCode);
                }
                page++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{-1};
        }

        if (filteredRecords.isEmpty()) {
            return new int[]{-1};
        }
        
        System.out.println(filteredRecords);

        // Calculate average weight per user
        HashMap<Integer, List<Double>> userWeights = new HashMap<>();
        for (JSONObject record : filteredRecords) {
            int userId = record.getInt("userId");
            double weight = record.getJSONObject("meta").getDouble("weight");
            userWeights.computeIfAbsent(userId, k -> new ArrayList<>()).add(weight);   
        }
        
        System.out.println("User Weight:: "+userWeights);

        HashMap<Integer, Double> userAverages = new HashMap<>();
        for (HashMap.Entry<Integer, List<Double>> entry : userWeights.entrySet()) {
            double sum = 0;
            for (double weight : entry.getValue()) {
                sum += weight; 
            }
            double avg = sum / entry.getValue().size();
            userAverages.put(entry.getKey(), avg);
            
            System.out.println("Weight Entry Sum: "+sum+" :: Weight Entry Count: "+entry.getValue().size()+" :: Weight Average: "+avg);
        }

        List<Integer> resultIds = new ArrayList<>();
        for (JSONObject record : filteredRecords) {
            int userId = record.getInt("userId");
            double currentWeight = record.getJSONObject("meta").getDouble("weight");
            int currentBreathing = record.getJSONObject("vitals").getInt("breathingRate");
            double avgWeight = userAverages.get(userId);
            if (currentWeight > avgWeight && currentBreathing > breathingRate) {
                resultIds.add(record.getInt("id"));
            }
        }

        if (resultIds.isEmpty()) {
            return new int[]{-1};
        }

        Collections.sort(resultIds);
        int[] result = new int[resultIds.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultIds.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        // Example usage
        int[] result = getRecordsDiagnosed(3, 20, 2);
        for (int id : result) {
            System.out.println("ID: "+id);
        }
    }
}
