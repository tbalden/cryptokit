java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -v ./1.txt

java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -e ./1.txt ./1p.txt > encode-result.txt

java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -d ./1e.txt ./1p.txt > decode-result.txt

java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App -v ./1e.txt

java -cp target/cryptokit-0.1.0-SNAPSHOT.jar org.tbalden.cryptokit.App
