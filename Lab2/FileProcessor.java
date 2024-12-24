package Lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
    public void createDict(String inName, String outName) throws IOException {
        File inFile = new File(inName);
        if (inName.length() == 0 || outName.length() == 0) {
            throw new IOException("The file name is not specified.");
        }
        if (!inFile.exists()) {
            throw new IOException("The file '" + inName + "' not found.");
        }
        if (inFile.length() == 0) {
            throw new IOException("The file '" + inName + "' is empty.");
        }
        Map<Character, Integer> freqDict = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
            int character;
            while ((character = reader.read()) != -1) {
                char c = (char) character;
                if (freqDict.containsKey(c)) {
                    freqDict.put(c, freqDict.get(c) + 1);
                }
                else {
                    freqDict.put(c, 1);
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outName))) {
            for (Map.Entry<Character, Integer> entry : freqDict.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        }
    }
}
