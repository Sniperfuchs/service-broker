java -jar .\osb-checker-kotlin-1.1.3.jar  | out-file "osb-checker-results.txt" -encoding utf8
cat .\osb-checker-results.txt
Read-Host -Prompt "Press Enter to exit"