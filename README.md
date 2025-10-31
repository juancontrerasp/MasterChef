# Master Chef Celebrity API 🍳

API REST para la gestión de recetas de cocina del programa Master Chef Celebrity.

## 🚀 Tecnologías

- Java 17
- Spring Boot 3.2.0
- MongoDB
- Maven
- Swagger/OpenAPI
- JUnit 5 + Mockito
- GitHub Actions
- Azure Web App
## 📚 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/recetas/televidente` | Crear receta de televidente |
| POST | `/api/recetas/participante` | Crear receta de participante |
| POST | `/api/recetas/chef-jurado` | Crear receta de chef jurado |
| GET | `/api/recetas` | Obtener todas las recetas |
| GET | `/api/recetas/{consecutivo}` | Obtener receta por ID |
| GET | `/api/recetas/participantes` | Obtener recetas de participantes |
| GET | `/api/recetas/televidentes` | Obtener recetas de televidentes |
| GET | `/api/recetas/chefs-jurados` | Obtener recetas de chefs |
| GET | `/api/recetas/temporada/{temporada}` | Obtener por temporada |
| GET | `/api/recetas/buscar?ingrediente=X` | Buscar por ingrediente |
| PUT | `/api/recetas/{consecutivo}` | Actualizar receta |
| DELETE | `/api/recetas/{consecutivo}` | Eliminar receta |

## 📝 Ejemplos de Uso

### Crear Receta de Televidente
```bash
curl -X POST "http://localhost:8080/api/recetas/televidente" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Bandeja Paisa",
    "ingredientes": ["Frijoles", "Arroz", "Carne", "Huevo"],
    "pasos": ["Cocinar frijoles", "Preparar arroz", "Freír carne"],
    "nombreChef": "María López"
  }'
```

### Buscar por Ingrediente
```bash
curl "http://localhost:8080/api/recetas/buscar?ingrediente=arroz"
```