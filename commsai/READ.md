# COMMSAI

TODO generate this

```
curl -X POST http://localhost:8082/deliverables \
  -H "Content-Type: application/json" \
  -d '{
        "message": "Hello, this is a test deliverable"
      }'
      
curl -X DELETE http://localhost:8082/deliverables/abc
      

curl -X POST http://localhost:8082/camel/deliverables \
  -H "Content-Type: application/json" \
  -d '{
        "uuid": "123e4567-e89b-12d3-a456-426614174000",
        "message": "Hello, this is a test deliverable"
      }'
```

```
docker exec -it broker kafka-topics --create \
  --topic deliverables \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1 \
  --config cleanup.policy=compact

docker exec -it broker kafka-topics --list \
  --bootstrap-server localhost:9092  

docker exec -it broker kafka-console-consumer \
  --topic deliverables \
  --bootstrap-server localhost:9092 \
  --from-beginning

docker exec -it broker kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic deliverables  

>{"transaction_id": "tx001", "amount": 120.0, "country": 0, "merchant": 0}
>{"transaction_id": "tx001", "amount": 120.0, "country": 0, "merchant": 0}
>{"transaction_id": "tx006", "amount": 75.0,  "country": 0, "merchant": 0}
>{"transaction_id": "tx007", "amount": 430.0, "country": 1, "merchant": 1}
>
```

