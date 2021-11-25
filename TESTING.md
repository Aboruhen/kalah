# Testing in kubernetes
    * The assumpsion is that kubernetes is pre defined, spined up somewhere.
    * you should have an access to it.
    * You should have a permissions to push new images which will be used by kubernetes
##
 1. run `mvn clean package` - it will generate docker images
 2. run `mvn dockerfile:push` - to push new images into your local registry
 3. run `helmfile sync --skip-deps` - to deploy new configurations
    1. if pods are not restarted remove deployments manually
 4. for easy access to service (without ingress) we can do a port-forward
    1. do it for a service `kalah-game-<id>`
 5. open web page on your forward port. Like `localhost:8080`
    1. try to play a game
 ----
 6. Connect telepresence
    1. Install the Telepresence CLI - [Guide](https://www.telepresence.io/docs/latest/howtos/intercepts/)
    2. `telepresence connect`
       1. if connect doesn't work because if  Traffic Manager component re-install it [Telepresence Traffic Manager](https://www.telepresence.io/docs/latest/install/helm/)
    3. `telepresence list`
    4. `kubectl get service kalah-metrics --output yaml`
    5. `telepresence intercept kalah-metrics --port 8081 --env-file ~/kalah-metrics-env.env`
        Expected response:
        ``````
         Using Deployment kalah-metrics
         intercepted
             Intercept name    : kalah-metrics
             State             : ACTIVE
             Workload kind     : Deployment
             Destination       : 127.0.0.1:8081
             Volume Mount Error: sshfs is not installed on your local machine
         Intercepting      : all TCP connections
         Intercepting all traffic to your service. To route a subset of the traffic instead, use a personal intercept. You can enable personal intercepts by authenticating to the Ambassador Developer Control Plane with "telepresence login".
    6. Run same service on yor machine with respective port that was defined on step 5. (`--port 8081`)
       1. to run service properly env variable should be used from step 5 - `kalah-metrics-env.env`
    7. Enjoy a debug or bug fixing
 


## Issues:
[Telepresence connect fails connecting to the traffic-manager](https://github.com/telepresenceio/telepresence/issues/1682)