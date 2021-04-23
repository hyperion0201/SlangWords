import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SlangWord {
    private HashMap<String, List<String>> slangMap;
    private HashMap<String, List<String>> definitionMap;
    private ArrayList<String> searchedMap;

    private static final String DATA_PATH = "./data/dictionary.txt";
    private static final String SEARCH_HISTORY_PATH = "./data/history.txt";

    public SlangWord() {
        slangMap = new HashMap<String, List<String>>();
        definitionMap = new HashMap<String, List<String>>();
        searchedMap = new ArrayList<String>();
    }
    private void _loadHistorySession() throws Exception {
        try {
            var fr = new FileReader(SEARCH_HISTORY_PATH);
            var br = new BufferedReader(fr);
            String currentLine = null;

            while ((currentLine = br.readLine()) != null) {
                searchedMap.add(currentLine.toString());
            }
            br.close();
            fr.close();
        }
        catch (Exception e) {
            System.out.print("No history exist. Launching new session...");
        }

    }
    public void _initializeSession() throws Exception {
        try {
            _loadHistorySession();
            var fileReader = new FileReader(DATA_PATH);
            var bufferReader = new BufferedReader(fileReader);
            String currentLine = null;

            while ((currentLine = bufferReader.readLine()) != null) {
                // Slang - Definition list handling
                var words = currentLine.split("`");
                var definitions = words[1].split("[|]");
                _trimingSpaces(definitions);
                var definitionsList = Arrays.asList(definitions);
                slangMap.put(words[0], definitionsList);

                // Definition - Slang handling
                for (String def : definitions) {
                    definitionMap.put(def, _buildSlangsByDefinition(def, words[0]));
                }
            }

            bufferReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void displaySlang() {
        for (String key : slangMap.keySet()) {
            System.out.println("Slang : " + key);
            displayList(slangMap.get(key));
        }
    }

    public void displayDef() {
        for (String key : definitionMap.keySet()) {
            System.out.println("Def : " + key);
            displayList(definitionMap.get(key));
        }
    }


}
