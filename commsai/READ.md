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

