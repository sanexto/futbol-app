### Construir imágen
```bash
docker build -t futbol-app:v1.0.0 .
```

### Ejecutar contenedor
```bash
docker run --name futbol-app-container -p 8080:8080 futbol-app:v1.0.0
```

### Swagger
URL: http://localhost:8080/swagger-ui.html
