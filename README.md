# Urdu Searching Dictionary

Fast **Urdu prefix search and autocomplete** in plain Java using a **Trie (prefix tree)**.

The project loads approximately **149,000 Urdu words** and provides efficient autocomplete suggestions for Urdu prefixes.

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Dependencies](https://img.shields.io/badge/Dependencies-None-brightgreen)
![Encoding](https://img.shields.io/badge/Encoding-UTF--8-blue)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-lightgrey)

---

## Table of Contents

* [Features](#features)
* [Quick Start](#quick-start)
* [Example Output](#example-output)
* [Project Structure](#project-structure)
* [How It Works](#how-it-works)
* [Usage](#usage)
* [Dictionary Format](#dictionary-format)
* [Urdu and Unicode Handling](#urdu-and-unicode-handling)
* [Windows UTF-8 Setup](#windows-utf-8-setup)
* [macOS and Linux](#macos-and-linux)
* [Troubleshooting](#troubleshooting)
* [Current Limitations](#current-limitations)
* [Changes in This Version](#changes-in-this-version)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License and Data Attribution](#license-and-data-attribution)
* [Author](#author)

---

## Features

* Urdu autocomplete using a Trie data structure
* Fast prefix-based searching
* Approximately **149,000+ Urdu dictionary entries**
* UTF-8 dictionary loading
* UTF-8 Urdu terminal output
* Duplicate-word prevention
* Recursive retrieval of matching words
* No external Java dependencies
* Works with Windows, macOS, and Linux terminals that support UTF-8

---

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/chanderrajput/UrduSearchingDictionary.git
cd UrduSearchingDictionary
```

### 2. Compile

```bash
javac -encoding UTF-8 *.java
```

### 3. Run

#### Windows PowerShell

```powershell
chcp 65001

[Console]::InputEncoding = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
$OutputEncoding = [Console]::OutputEncoding

java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

#### macOS / Linux

```bash
java -cp . Test
```

> If Urdu appears as `????`, boxes, or corrupted symbols, see [Windows UTF-8 Setup](#windows-utf-8-setup) and [Troubleshooting](#troubleshooting).

---

## Example Output

Searching for:

```text
آئر
```

can return:

```text
Words loaded: 149466
Autocomplete results:
آئر لینڈ
آئرس
آئرستان
آئرستانی
آئرستانیوں
آئرسز
آئرش
آئرشی
آئرن
آئرہ
آئرین
```

---

## Requirements

| Requirement   | Description                   |
| ------------- | ----------------------------- |
| Java JDK      | Java 17+ recommended          |
| Tested JDK    | Eclipse Adoptium / OpenJDK 25 |
| Terminal      | UTF-8 capable terminal        |
| File encoding | UTF-8                         |
| Dependencies  | None                          |

Verify Java:

```bash
java -version
javac -version
```

If `java` works but `javac` does not, install a full **JDK**, not only a JRE.

---

## Project Structure

```text
UrduSearchingDictionary/
│
├── AutoCompleteDictionaryTrie.java
├── Filing.java
├── Node.java
├── Test.java
├── UrduNames.txt
├── README.md
└── .gitignore
```

| File                              | Responsibility                                               |
| --------------------------------- | ------------------------------------------------------------ |
| `AutoCompleteDictionaryTrie.java` | Trie insertion, prefix searching, and autocomplete retrieval |
| `Node.java`                       | Represents a single Trie node and its child nodes            |
| `Filing.java`                     | Loads the Urdu dictionary using UTF-8                        |
| `Test.java`                       | Main application entry point                                 |
| `UrduNames.txt`                   | UTF-8 Urdu dictionary, one entry per line                    |
| `README.md`                       | Project documentation                                        |

Recommended `.gitignore`:

```gitignore
*.class
bin/
out/
```

Compiled `.class` files are build output and normally should not be committed.

---

## How It Works

A **Trie** stores words character by character.

Words that share the same prefix also share the same path through the Trie.

For example:

```text
Root
 └── پ
      └── ا
           └── ک
                ├── س
                │    └── ت
                │         └── ا
                │              └── ن   → پاکستان
                │
                └── ی
                     └── ز
                          └── ہ        → پاکیزہ
```

This makes prefix searching much more efficient than scanning every dictionary word.

### Search Flow

| Step | Method / Component  | Purpose                                   |
| ---- | ------------------- | ----------------------------------------- |
| 1    | `Filing`            | Reads Urdu words from the dictionary      |
| 2    | `addWord()`         | Inserts each word into the Trie           |
| 3    | `searchNode()`      | Finds the node for the requested prefix   |
| 4    | `FetchAll()`        | Starts autocomplete retrieval             |
| 5    | Recursive traversal | Finds all complete words below the prefix |

---

## Complexity

Let:

* **L** = length of a word or prefix
* **K** = number of autocomplete matches
* **N** = number of dictionary entries

| Operation             | Approximate Cost                            |
| --------------------- | ------------------------------------------- |
| Insert a word         | `O(L)`                                      |
| Locate a prefix       | `O(L)`                                      |
| Return matching words | Depends on the number and length of matches |

The key advantage of a Trie is that locating the starting point for a prefix does not require scanning all `N` dictionary entries.

---

## Usage

Create the Trie:

```java
AutoCompleteDictionaryTrie trieDictionary =
        new AutoCompleteDictionaryTrie();
```

Load the dictionary:

```java
Filing file = new Filing();

for (String word : file.dict()) {
    trieDictionary.addWord(word);
}
```

Display the dictionary size:

```java
System.out.println(
        "Words loaded: " + trieDictionary.size()
);
```

Search for an Urdu prefix:

```java
trieDictionary.FetchAll("آئر");
```

You can search for another prefix:

```java
trieDictionary.FetchAll("پاک");
```

or:

```java
trieDictionary.FetchAll("محمد");
```

---

## Dictionary Format

`UrduNames.txt` should be:

* Plain text
* UTF-8 encoded
* One word or phrase per line

Example:

```text
پاکستان
پاکستانی
پاکستانیوں
آئرلینڈ
آئرش
آئرن
```

### Check Encoding in VS Code

Open `UrduNames.txt`.

Look at the encoding indicator in the bottom-right corner of VS Code.

It should show:

```text
UTF-8
```

If it does not:

1. Click the encoding indicator.
2. Select **Reopen with Encoding**.
3. Choose **UTF-8**.
4. Confirm that the Urdu text displays correctly.
5. Select **Save with Encoding**.
6. Choose **UTF-8**.

The `.java` files should also be saved as UTF-8 because the source code contains Urdu literals.

---

## Urdu and Unicode Handling

Urdu uses Arabic-script Unicode characters, and visually similar letters do not always have the same Unicode code point.

### Common Character Variants

| Urdu Character | Arabic Variant | Issue                                      |
| -------------- | -------------- | ------------------------------------------ |
| `ی` — U+06CC   | `ي` — U+064A   | Different Unicode characters               |
| `ک` — U+06A9   | `ك` — U+0643   | Different Trie branches                    |
| `ہ` — U+06C1   | `ه` — U+0647   | Search may fail despite similar appearance |

For example, a word stored using:

```text
پاکستانی
```

may not match user input containing the Arabic `ي` instead of the Urdu `ی`.

### Invisible Characters

Copied Urdu text can also contain:

* Zero-width non-joiner — `U+200C`
* Zero-width joiner — `U+200D`
* Diacritics
* Extra spaces

These characters are meaningful to Java and may cause two visually similar strings to be treated differently.

### Recommended Future Improvement

Normalize both dictionary entries and search input before storing or searching.

A future normalization layer could:

* Convert Arabic letter variants to Urdu equivalents
* Remove zero-width characters
* Remove optional diacritics
* Trim whitespace
* Apply Unicode normalization such as NFC

The same normalization function should be applied during both:

```java
addWord(...)
```

and:

```java
FetchAll(...)
```

---

## Windows UTF-8 Setup

Windows PowerShell may require explicit UTF-8 configuration.

Without it, Urdu may appear as:

```text
????
???????
```

or:

```text
╬▒╬...
```

These are usually **encoding problems**, not Trie problems.

### Step 1 — Set the Console Code Page

```powershell
chcp 65001
```

Expected:

```text
Active code page: 65001
```

Check it at any time with:

```powershell
chcp
```

---

### Step 2 — Configure PowerShell Encoding

```powershell
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
$OutputEncoding = [Console]::OutputEncoding
```

---

### Step 3 — Test Urdu Before Running Java

```powershell
Write-Host "اردو پاکستان آئرلینڈ"
```

Expected:

```text
اردو پاکستان آئرلینڈ
```

If this test is already corrupted, the problem is the terminal configuration or font rather than the Java code.

---

### Step 4 — Compile

```powershell
javac -encoding UTF-8 *.java
```

---

### Step 5 — Run

```powershell
java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

> Keep the JVM `-D...` arguments quoted when using PowerShell.

Without quotes, some PowerShell environments may interpret the arguments incorrectly and produce:

```text
Error: Could not find or load main class .encoding=UTF-8
```

Use:

```powershell
java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

---

### Complete Windows Command Sequence

```powershell
chcp 65001

[Console]::InputEncoding = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
$OutputEncoding = [Console]::OutputEncoding

javac -encoding UTF-8 *.java

java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

---

### Make UTF-8 Automatic in PowerShell

PowerShell encoding settings normally apply only to the current terminal session.

To configure them automatically, open your PowerShell profile:

```powershell
notepad $PROFILE
```

If the file does not exist:

```powershell
New-Item -ItemType File -Path $PROFILE -Force
notepad $PROFILE
```

Add:

```powershell
chcp 65001 > $null

[Console]::InputEncoding = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
$OutputEncoding = [Console]::OutputEncoding
```

Save the file and restart PowerShell or VS Code.

After that, you normally only need:

```powershell
javac -encoding UTF-8 *.java
java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

---

## macOS and Linux

Most modern macOS and Linux environments already use UTF-8.

Compile:

```bash
javac -encoding UTF-8 *.java
```

Run:

```bash
java -cp . Test
```

If Urdu is corrupted, check your locale:

```bash
locale
```

It should normally contain a UTF-8 locale.

For example:

```text
LANG=en_US.UTF-8
```

If necessary:

```bash
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
```

---

## Troubleshooting

| Problem                                             | Likely Cause                                      | Solution                                        |
| --------------------------------------------------- | ------------------------------------------------- | ----------------------------------------------- |
| Urdu displays as `????`                             | Output encoding mismatch                          | Configure UTF-8 and use the Java stdout flags   |
| Urdu displays as `╬▒╬`                              | UTF-8 bytes decoded using another code page       | Run the full PowerShell UTF-8 setup             |
| Urdu displays as boxes `□□□`                        | Font does not contain Urdu glyphs                 | Use a font with Arabic/Urdu glyph support       |
| `.encoding=UTF-8` treated as a class name           | PowerShell parsed an unquoted JVM option          | Quote each `-D...` argument                     |
| `Could not find or load main class Test`            | `Test.class` does not exist or classpath is wrong | Compile first and run with `-cp .`              |
| `javac` is not recognized                           | JDK missing or not on `PATH`                      | Install a JDK and configure `PATH`              |
| `git` is not recognized                             | Git missing or not on `PATH`                      | Install Git and reopen the terminal             |
| Urdu worked before but is broken in a new terminal  | PowerShell UTF-8 settings were reset              | Configure UTF-8 again or update `$PROFILE`      |
| Search returns no match for a visually correct word | Unicode character variants                        | Normalize Urdu input and dictionary values      |
| Too many results                                    | Prefix is too short                               | Use a longer prefix or implement a result limit |

---

## Common Errors

### `Could not find or load main class Test`

If you see:

```text
Error: Could not find or load main class Test
Caused by: java.lang.ClassNotFoundException: Test
```

compile first:

```powershell
javac -encoding UTF-8 *.java
```

Verify:

```powershell
dir Test.class
```

Then:

```powershell
java "-Dstdout.encoding=UTF-8" "-Dstderr.encoding=UTF-8" -cp . Test
```

---

### `javac` Is Not Recognized

Check:

```powershell
java -version
javac -version
```

If `javac` is unavailable, install a Java **JDK** and add its `bin` directory to your Windows `PATH`.

---

### `git` Is Not Recognized

Install Git on Windows:

```powershell
winget install --id Git.Git -e --source winget
```

Close and reopen VS Code.

Then verify:

```powershell
git --version
```

---

## Current Limitations

The current implementation is intentionally simple.

### `FetchAll()` Prints Results Directly

`FetchAll()` writes autocomplete results directly to the console.

A better reusable API would return:

```java
List<String>
```

instead.

That would make the Trie easier to use from:

* REST APIs
* Web applications
* Desktop applications
* Android applications
* Unit tests

---

### No Result Limit

A short prefix may return thousands of results.

A future API could support:

```java
fetchAll(String prefix, int limit)
```

and stop traversal once the limit is reached.

---

### No Ranking

Results are returned according to Trie traversal order rather than popularity or frequency.

A production autocomplete system could rank results using:

* Word frequency
* Search history
* Usage statistics
* Custom relevance scores

---

### No Urdu Normalization

The current implementation does not automatically normalize Arabic/Urdu Unicode character variants.

See [Urdu and Unicode Handling](#urdu-and-unicode-handling).

---

### Memory Usage

Each Trie node currently stores its child nodes in a `HashMap`.

This is simple and flexible, but a large Trie may consume more memory than a compressed or specialized Trie representation.

Possible future alternatives include:

* Compact child arrays
* Radix trees
* Compressed Tries
* Ternary search trees

Optimization should be based on actual profiling rather than assumptions.

---

## Changes in This Version

### Trie Fixes

* Fixed incorrect Trie traversal
* Fixed child-node creation
* Fixed autocomplete recursion
* Fixed incorrect combinations of unrelated Trie branches
* Removed broken temporary autocomplete state
* Added duplicate-word prevention
* Improved handling of missing prefixes

### File Handling

* Improved dictionary loading
* Explicit UTF-8 dictionary reading
* Better handling of large Urdu word collections

### Terminal and Encoding

* Added Windows PowerShell UTF-8 setup
* Added explicit Java stdout/stderr UTF-8 configuration
* Added troubleshooting for `????`
* Added troubleshooting for mojibake such as `╬▒╬`
* Added PowerShell JVM argument guidance

---

## Roadmap

### Search Engine

* [ ] Urdu Unicode normalization
* [ ] Return `List<String>` instead of printing
* [ ] Result limits
* [ ] Result pagination
* [ ] Word-frequency ranking
* [ ] Fuzzy search
* [ ] Typo correction
* [ ] Spell checking

### Dictionary

* [ ] Urdu-to-English meanings
* [ ] English-to-Urdu meanings
* [ ] Frequency data
* [ ] Additional dictionary sources

### Applications

* [ ] REST API
* [ ] Spring Boot backend
* [ ] Search-as-you-type web interface
* [ ] Desktop application
* [ ] Android application

---

## Contributing

Contributions, bug fixes, and improvements are welcome.

### Typical Workflow

```bash
git pull
```

Make your changes.

Check:

```bash
git status
```

Stage:

```bash
git add .
```

Commit:

```bash
git commit -m "Describe your changes"
```

Push:

```bash
git push
```

### Contribution Guidelines

Please:

* Save all source files using UTF-8
* Compile using `javac -encoding UTF-8`
* Test Urdu output before submitting changes
* Test autocomplete with multiple Urdu prefixes
* Keep dictionary and search normalization consistent
* Avoid committing `.class` files

---

## License and Data Attribution

No explicit open-source license is currently documented.

If this project is intended for public reuse, consider adding a `LICENSE` file.

Common choices include:

| License    | Description                                     |
| ---------- | ----------------------------------------------- |
| MIT        | Simple and permissive                           |
| Apache 2.0 | Permissive with explicit patent terms           |
| GPL v3     | Requires derivative works to remain open source |

### Dictionary Data

If `UrduNames.txt` was obtained from an external source, document:

* Dataset name
* Original author or organization
* Source URL
* License
* Any modifications made to the data

The source-code license and dictionary-data license may be different.

---

## Author

**Chander Kumar**

* GitHub: [github.com/chanderrajput](https://github.com/chanderrajput)
* Repository: [github.com/chanderrajput/UrduSearchingDictionary](https://github.com/chanderrajput/UrduSearchingDictionary)

---

## Support

If you find a bug or have an improvement idea, open an issue in the GitHub repository.

When reporting Urdu display problems, include:

* Operating system
* Java version
* Terminal application
* Output of `chcp` on Windows
* Example of the corrupted output
