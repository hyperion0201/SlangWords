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
    private List<String> _buildSlangsByDefinition(String def, String slang) {
        var list = new ArrayList<String>();
        // Find a def-slang hashmap exist.
        var existDefHash = definitionMap.get(def);
        if (existDefHash == null) {
            // mean this is the first time handle this def word.
            list.add(slang);
            return list;
        } else {
            existDefHash.add(slang);
            return existDefHash;
        }
    }

    private List<String> _queryAllDefinitionsBySlang(String query) {
        // Search by slang required 100% accurracy in keywords.
        return slangMap.get(query);
    }

    public void SearchBySlang(String query) {
        _pushHistory(query);
        var defFound = _queryAllDefinitionsBySlang(query);
        if (defFound != null) {
            System.out.print("Found " + query + " with definitions: ");
            displayList(defFound);
        } else {
            System.out.println("No definition found with slang word provided.");
        }
    }

    private List<String> _queryAllSlangsByDefinition(String query) {
        // Not same as slang search, search by definition doesn't require accuracy in keyword.
        // So, if any definition includes the keyword, get em.

        // Gather all possible def words that matched the keyword
        var defForSearching = new ArrayList<String>();
        for (String key : definitionMap.keySet()) {
            if (key.contains(query)) {
                defForSearching.add(key);
            }
        }

        // Performing search.
        var slangs = new ArrayList<String>();
        for (String def : defForSearching) {
            if (definitionMap.get(def) != null) {
                slangs.addAll(definitionMap.get(def));
            }
        }

        return slangs;
    }

    public void SearchByDefinition(String query) {
        _pushHistory(query);
        var slangFound = _queryAllSlangsByDefinition(query);
        if (slangFound.size() == 0) {
            System.out.println("No slang found with word provided.");
        } else {
            System.out.print("Found " + slangFound.size() + " slang word(s) matched the keyword : ");
            displayList(slangFound);
        }
    }

    private void displayList(List<String> list) {
        for (String item : list) {
            System.out.print(item + ", ");

        }
    }

    private void _trimingSpaces(String[] words) {
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
    }

    private void _pushHistory(String value) {
        searchedMap.add(value);
    }

    public void displayHistory() {
        System.out.println("======= SEARCH HISTORY ========");
        for (String item : searchedMap) {
            System.out.print(item + ", ");
        }
    }
    
    public boolean isSlangFound(String slang) {
        return slangMap.get(slang) != null ? true : false;
    }

    private void _onCreateSlangWord(String slang, String def, Boolean isOverride) throws Exception {
        var slangFound = isSlangFound(slang);
        if (slangFound) {
            if (!isOverride) {
                // mean slang should be one or more definitions
                var currentDef = slangMap.get(slang);
                List<String> newDef =  new ArrayList<String>(currentDef);
                newDef.add(def);
                slangMap.replace(slang, newDef);
            } else {
                // override slang
                var newDef = new ArrayList<String>();
                newDef.add(def);
                slangMap.replace(slang, newDef);
            }
        }
        else {
            var newDef = new ArrayList<String>();
            newDef.add(def);
            slangMap.put(slang, newDef);
        }
    }

    public void addNewSlangWord(String slang, String def, Boolean isOverride) {
        try {
            _onCreateSlangWord(slang, def, isOverride);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _onEditSlangWord(String slang, String newSlang) throws Exception {
        var slangDef = slangMap.get(slang);
        slangMap.put(newSlang, slangDef);
        slangMap.remove(slang);
    }

    public void editSlangWord(String slang, String newSlang) {
        try {
            _onEditSlangWord(slang, newSlang);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void _onDeleteSlangWord(String slang) throws Exception {
        slangMap.remove(slang);
    }

    public void deleteSlangWord(String slang) {
        try {
            _onDeleteSlangWord(slang);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String _getRandomSlangKey() {
        Random r = new Random();
        var keySet = new ArrayList<String>(slangMap.keySet());
        return keySet.get(r.nextInt(keySet.size()));
    }

    private String _getRandomDefinitionKey() {
        Random r = new Random();
        var keySet = new ArrayList<String>(definitionMap.keySet());
        return keySet.get(r.nextInt(keySet.size()));
    }

    public void getRandomSlangWord() {
        var randomKey = _getRandomSlangKey();
        var def = slangMap.get(randomKey);
        if (randomKey != null) {
            System.out.println("Random slang word: ");
            System.out.println("Slang : " + randomKey);
            System.out.print("Definition : ");
            displayList(def);
        } else {
            System.out.println("No slang word found.");
        }
    }
    
    private void _onReloadSession() {
        slangMap = new HashMap<String, List<String>>();
        definitionMap = new HashMap<String, List<String>>();
        try {
            _initializeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadSession() {
        _onReloadSession();
    }

}
