
rub-build:
	mvn clean package

	docker build -t kalah-game:latest --build-arg JAR_FILE=kalah-game.jar ./kalah-game
	docker tag kalah-game localhost:5000/kalah-game
	docker push localhost:5000/kalah-game:latest

	docker build -t kalah-metrics:latest --build-arg JAR_FILE=kalah-metrics.jar ./kalah-metrics
	docker tag kalah-metrics localhost:5000/kalah-metrics
	docker push localhost:5000/kalah-metrics:latest
