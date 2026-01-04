package com.airtribe.meditrack.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    
    /**
     * Read CSV file and return lines as list
     * @param filePath Path to CSV file
     * @return List of string arrays (each array represents a row)
     * @throws IOException if file reading fails
     */
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return rows;
        }
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] values = line.split(",");
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }
                    rows.add(values);
                }
            }
        }
        
        return rows;
    }
    
    /**
     * Write data to CSV file
     * @param filePath Path to CSV file
     * @param data List of string arrays to write
     * @param append Whether to append or overwrite
     * @throws IOException if file writing fails
     */
    public static void writeCSV(String filePath, List<String[]> data, boolean append) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filePath),
                append ? java.nio.file.StandardOpenOption.CREATE : java.nio.file.StandardOpenOption.CREATE,
                append ? java.nio.file.StandardOpenOption.APPEND : java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
            
            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
    
    /**
     * Write single row to CSV
     * @param filePath Path to CSV file
     * @param row Row data as string array
     * @param append Whether to append or overwrite
     * @throws IOException if file writing fails
     */
    public static void writeCSVRow(String filePath, String[] row, boolean append) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filePath),
                append ? java.nio.file.StandardOpenOption.CREATE : java.nio.file.StandardOpenOption.CREATE,
                append ? java.nio.file.StandardOpenOption.APPEND : java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(String.join(",", row));
            writer.newLine();
        }
    }
}

