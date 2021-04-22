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
}
