
# FeedAnalyser Project v002

Project for test rabbit, pg, jsoup in spring

## Some useful commands:
```
docker-compose down --rmi all || true
docker-compose -f docker-compose-infra.yml down --rmi all || true
docker-compose -f docker-compose-infra.yml up || true
docker-compose down
docker-compose up --build
```

### Helpers
```
-- in windows --
if you run docker compose and pg return any error - check windows services and stop pg there
-- for build --
open maven window
dropdown app\Lifecycle
toggle off "test" (crossed out circle) 
```