import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Filing {
    public static final ArrayList<String> Dictionary = new ArrayList<>();

    private final Path filePath;

    public Filing() {
        this("/home/chander/Desktop/UrduNames.txt");
    }

    public Filing(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    // FIX: this must return the dictionary because Test uses file.dict() in a for-each loop.
    public ArrayList<String> dict() {
        Dictionary.clear();

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {

            String poetName;

            while ((poetName = reader.readLine()) != null) {
                poetName = poetName.trim();

                if (!poetName.isEmpty()) {
                    Dictionary.add(poetName);
                }
            }

        } catch (IOException ex) {
            System.err.println("Could not read dictionary file: " + ex.getMessage());
        }

        return Dictionary;
    }
}