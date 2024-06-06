# Testing in kubernetes
    * The assumpsion is that kubernetes is pre defined, spined up somewhere.
    * you should have an access to it.
    * You should have a permissions to push new images which will be used by kubernetes

## Prerequisites
For colima don't forget to run colima with kubernetes
`colima stop`

`colima delete`

`colima start --cpu 4 --memory 8 --mount $HOME:w --with-kubernetes`
param: `--arch x86_64` to use non AppleM1 images

`docker run -d -p 5000:5000 --name registry registry:latest`

`docker rmi $(docker images | grep 'kalah')`

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

##
 1. run `mvn clean package` - it will generate docker images
 2. run commands to push new images into your local registry
    ```
    docker build -t kalah-game:latest --build-arg JAR_FILE=kalah-game.jar ./kalah-game
    docker build -t kalah-metrics:latest --build-arg JAR_FILE=kalah-metrics.jar ./kalah-metrics
    docker tag kalah-game localhost:5000/kalah-game
    docker tag kalah-metrics localhost:5000/kalah-metrics
    docker push localhost:5000/kalah-game:latest
    docker push localhost:5000/kalah-metrics:latest
    ```
 3. run `helmfile sync --skip-deps` - to deploy new configurations
    1. if pods are not restarted remove deployments manually `helmfile destroy`
 4. for easy access to service (without ingress) we can do a port-forward
    1. do it for a service `kalah-game-<id>`
 5. open web page on your forward port. Like `localhost:8080`
    1. try to play a game
 ----
 6. Connect mirrord
    1. Install the MirrorD CLI - [Guide](https://mirrord.dev/)
    
 


## Issues:
[Telepresence connect fails connecting to the traffic-manager](https://github.com/telepresenceio/telepresence/issues/1682)