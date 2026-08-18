public class Test {
    public static void main(String[] args) {
        String dictionaryPath = args.length > 0
                ? args[0]
                : "UrduNames.txt";
                

        AutoCompleteDictionaryTrie trieDictionary =
                new AutoCompleteDictionaryTrie();

        Filing file = new Filing(dictionaryPath);

        for (String word : file.dict()) {
            trieDictionary.addWord(word);
        }

        System.out.println("Words loaded: " + trieDictionary.size());
        System.out.println("Autocomplete results:");

        trieDictionary.FetchAll("آئر");
    }
}