# Aho-Corasick Algorithm

Java program for finding multiple patterns in a text. KMP is used for comparison.

## Run

```bash
javac *.java
java TestCases
java Benchmark
```

`TestCases.java` checks correctness. `Benchmark.java` measures execution time and saves it in `results.csv`.

## How it works

1. Add all patterns to a **trie**.
2. Build **failure links** using BFS. They help continue when a character does not match.
3. Store matched patterns at each node, including matches from failure links.
4. Scan the text once and report all occurrences.

## Complexity

**S** = total pattern length, **N** = text length, **P** = number of patterns, **Z** = number of matches.

| Algorithm | Preparation | Searching | Memory (excluding text and reported matches) |
| --- | --- | --- | --- |
| Aho-Corasick | O(S) for trie/failure links (lecture model) | O(N + Z) | O(S) trie nodes and links with hash maps, plus stored outputs |
| KMP separately for P patterns | O(S) for prefix tables | O(P * N + Z) | O(longest pattern length), if patterns are processed one by one |

The trie shares prefixes between patterns. Failure links avoid restarting the search. Aho-Corasick is useful for many patterns but needs memory for the trie and outputs.

## Experiments

Tested **10, 100, 1,000 patterns** with text lengths **1,000, 10,000, 100,000** (9 cases). Patterns have 4–8 characters (`a`–`f`); data uses a fixed random seed.

Each test runs **5 times** with `System.nanoTime()`. The average includes building and searching for both algorithms. Both results are checked for equality. Full timings are in `results.csv`.

Measured on Linux with OpenJDK 21 (milliseconds):

| Patterns | Text length | Aho-Corasick avg (ms) | KMP avg (ms) | Matches |
| ---: | ---: | ---: | ---: | ---: |
| 10 | 1,000 | 0.514 | 0.838 | 2 |
| 10 | 10,000 | 1.874 | 2.533 | 9 |
| 10 | 100,000 | 3.513 | 5.382 | 365 |
| 100 | 1,000 | 0.352 | 0.415 | 21 |
| 100 | 10,000 | 0.520 | 5.101 | 177 |
| 100 | 100,000 | 3.423 | 52.850 | 1629 |
| 1,000 | 1,000 | 2.218 | 3.929 | 156 |
| 1,000 | 10,000 | 2.541 | 52.086 | 1690 |
| 1,000 | 100,000 | 5.029 | 533.440 | 19821 |

## Discussion

Aho-Corasick is faster with many patterns because it scans the text once. KMP is simpler and uses less memory for one pattern, but it scans the text separately for every pattern. Aho-Corasick needs extra memory and preparation time. Results can vary between computers.
