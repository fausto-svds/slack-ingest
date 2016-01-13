
#Build
docker build -t svds/public-channels-webhooks .

#Run
docker run -p 8080:8080 svds/public-channels-webhooks java -jar target/public-channels-0.0.1-SNAPSHOT.jar
