import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Solution {

    public static int[] getRecordsDiagnosed(int doctorId, int breathingRate, int diagnosisId) {

        List<JSONObject> records = fetchAllRecords(doctorId, diagnosisId);

        if (records.isEmpty()) {
            return new int[]{-1};
        }

        Map<Integer, Double> avgWeightByUser = calculateAverageWeight(records);

        List<Integer> resultIds = filterRecords(records, avgWeightByUser, breathingRate);

        if (resultIds.isEmpty()) {
            return new int[]{-1};
        }

        Collections.sort(resultIds);
        return resultIds.stream().mapToInt(i -> i).toArray();
    }

    // -------------------------------
    // 1. FETCH API DATA
    // -------------------------------
    private static List<JSONObject> fetchAllRecords(int doctorId, int diagnosisId) {
        List<JSONObject> result = new ArrayList<>();
        int page = 1, totalPages = 1;

        try {
            while (page <= totalPages) {

                String urlString = "https://jsonmock.hackerrank.com/api/medical_records?page=" + page;
                URL url = new URI(urlString).toURL();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject response = new JSONObject(sb.toString());

                if (page == 1) {
                    totalPages = response.getInt("total_pages");
                }

                JSONArray data = response.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject record = data.getJSONObject(i);

                    int docId = record.getJSONObject("doctor").getInt("id");
                    int diagId = record.getJSONObject("diagnosis").getInt("id");

                    if (docId == doctorId && diagId == diagnosisId) {
                        result.add(record);
                    }
                }

                page++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // -------------------------------
    // 2. CALCULATE AVG WEIGHT PER USER
    // -------------------------------
    private static Map<Integer, Double> calculateAverageWeight(List<JSONObject> records) {

        Map<Integer, List<Double>> weights = new HashMap<>();

        for (JSONObject record : records) {
            int userId = record.getInt("userId");
            double weight = record.getJSONObject("meta").getDouble("weight");

            weights.computeIfAbsent(userId, k -> new ArrayList<>()).add(weight);
        }

        Map<Integer, Double> avg = new HashMap<>();

        for (Map.Entry<Integer, List<Double>> entry : weights.entrySet()) {
            double sum = 0;

            for (double w : entry.getValue()) {
                sum += w;
            }

            avg.put(entry.getKey(), sum / entry.getValue().size());
        }

        return avg;
    }

    // -------------------------------
    // 3. FINAL FILTER
    // -------------------------------
    private static List<Integer> filterRecords(
            List<JSONObject> records,
            Map<Integer, Double> avgWeightByUser,
            int breathingRateLimit) {

        List<Integer> result = new ArrayList<>();

        for (JSONObject record : records) {

            int userId = record.getInt("userId");
            double weight = record.getJSONObject("meta").getDouble("weight");
            int breathingRate = record.getJSONObject("vitals").getInt("breathingRate");

            double avgWeight = avgWeightByUser.get(userId);

            if (weight > avgWeight && breathingRate > breathingRateLimit) {
                result.add(record.getInt("id"));
            }
        }

        return result;
    }

    // -------------------------------
    // MAIN
    // -------------------------------
    public static void main(String[] args) {
        int[] result = getRecordsDiagnosed(3, 20, 2);

        for (int id : result) {
            System.out.println(id);
        }
    }
}