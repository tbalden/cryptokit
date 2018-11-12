java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -e ./phrase.txt ./password.txt > encode-result.txt

java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -d ./fake-phrase.txt ./password.txt > decode-result.txt

