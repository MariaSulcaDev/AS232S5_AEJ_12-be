# Cognitive Services - APIs de Traducción y Video Downloader con Spring Boot

**Servicios Cognitivos de Rapid API**, este proyecto integra múltiples APIs cognitivas para proporcionar servicios de traducción de texto y descarga de información de videos de YouTube. Utiliza Spring Boot WebFlux para crear un backend reactivo que consume servicios externos y almacena resultados en MongoDB.

**1. Cognitive Services**
<img src ="https://wakeupandcode.com/wp-content/uploads/2019/08/azure-cognitive-services-bootcamp-event-image.png" align="right" style="width: 200px"/>

- **Rapid API - Deep Translate**: Servicio de traducción de texto entre múltiples idiomas
- **Rapid API - YouTube Media Downloader**: Extractor de información y detalles de videos de YouTube
- **MongoDB**: Base de datos NoSQL para almacenamiento de solicitudes y respuestas

**2. Spring Boot**
<img src ="https://miro.medium.com/v2/resize:fit:716/1*98O4Gb5HLSlmdUkKg1DP1Q.png" align="right" style="height:60px; width: 200px"/>

- Java: JDK 17
- IDE: IntelliJ IDEA | Visual Studio Code | Codespace
- Maven: Apache Maven
- Frameworks: Spring Boot 3.5.5, Spring WebFlux (Reactive)

**3. Maven Dependencias:**
<img src ="https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Apache_Maven_logo.svg/1280px-Apache_Maven_logo.svg.png" align="right" style="width: 200px"/>

- spring-boot-starter-webflux
- spring-boot-starter-data-mongodb-reactive
- lombok
- reactor-test
- springdoc-openapi-starter-webflux-ui

## **Dependencias Spring WebFlux + MongoDB (NoSQL)**

Spring WebFlux | Data MongoDB Reactive | Project Reactor

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```

## **Dependencias Swagger para Spring WebFlux**

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

## **APIs Disponibles**

### 🌐 **API de Traducción**

Traduce texto entre múltiples idiomas utilizando Deep Translate de RapidAPI.

**Endpoints:**

- `POST /api/cognitive/translate` - Traducir texto
- `GET /api/cognitive/translations` - Obtener todas las traducciones
- `GET /api/cognitive/translations/{id}` - Obtener traducción por ID

**Ejemplo de uso:**

```bash
curl -X POST http://localhost:8081/api/cognitive/translate \
-H "Content-Type: application/json" \
-d '{
    "q": "Hello World!",
    "source": "en",
    "target": "es"
}'
```

**Respuesta:**

```json
{
    "data": {
        "translations": {
            "translatedText": [
                "¡Hola Mundo!"
            ]
        }
    }
}
```

### 📺 **API de Video Downloader**

Obtiene información detallada de videos de YouTube incluyendo metadatos, enlaces de descarga y calidades disponibles.

**Endpoints:**

- `GET /api/cognitive/video-details` - Obtener detalles de video
- `GET /api/cognitive/video-downloads` - Obtener todos los downloads
- `GET /api/cognitive/video-downloads/{id}` - Obtener download por ID

**Ejemplo de uso:**

```bash
curl -X GET "http://localhost:8081/api/cognitive/video-details?videoId=sqgSm8fWe1s&videos=auto&audios=auto"
```

**Respuesta:**

```json
{
    "id": "sqgSm8fWe1s",
    "title": "Título del video",
    "description": "Descripción del video",
    "channel": {
        "name": "Nombre del canal",
        "handle": "@nombrecanal",
        "avatarUrl": "https://...",
        "isVerified": true
    },
    "thumbnail": {
        "url": "https://...",
        "width": 1280,
        "height": 720
    },
    "video": {
        "url": "https://...",
        "quality": "720p",
        "sizeText": "10MB",
        "mimeType": "video/mp4"
    }
}
```

## **Configuración**

### **Base de Datos MongoDB**

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://[usuario]:[password]@[cluster].mongodb.net/?retryWrites=true&w=majority
      database: cognitive-apis
```

### **APIs Externas**

```yaml
translate:
  api:
    url: https://deep-translate1.p.rapidapi.com/language/translate/v2
    key: ${RAPIDAPI_KEY}
    host: deep-translate1.p.rapidapi.com

youtube:
  api:
    url: https://youtube-media-downloader.p.rapidapi.com/v2/video/details
    key: ${RAPIDAPI_KEY}
    host: youtube-media-downloader.p.rapidapi.com
```

### **Swagger/OpenAPI**

```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
    enabled: true
  swagger-ui:
    path: /swagger-ui/index.html
    enabled: true
```

## **Documentación API**

- **Swagger UI**: `http://localhost:8081/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`

## **Características**

✅ **Arquitectura Reactiva** con Spring WebFlux
✅ **Persistencia NoSQL** con MongoDB Reactive
✅ **APIs Externas** integradas con RapidAPI
✅ **Documentación Automática** con OpenAPI/Swagger
✅ **Logging Completo** de operaciones
✅ **Manejo de Errores** robusto
✅ **CORS Habilitado** para frontend
✅ **Almacenamiento Histórico** de solicitudes y respuestas

## **Idiomas Soportados (Traducción)**

- `en` - Inglés
- `es` - Español
- `fr` - Francés
- `de` - Alemán
- `it` - Italiano
- `pt` - Portugués
- `ru` - Ruso
- `zh` - Chino
- `ja` - Japonés
- `ko` - Coreano

## **Instalación y Ejecución**

1. **Clonar el repositorio**

```bash
git clone https://github.com/MariaSulcaDev/apis-cognitivas.git
cd apis-cognitivas
```

2. **Configurar variables de entorno**

```bash
export RAPIDAPI_KEY=tu_clave_de_rapidapi
```

3. **Compilar y ejecutar**

```bash
mvn clean compile
mvn spring-boot:run
```

4. **Acceder a la aplicación**

- API: `http://localhost:8081`
- Swagger UI: `http://localhost:8081/swagger-ui/index.html`

## **Estructura del Proyecto**

```
src/
├── main/
│   ├── java/pe/edu/vallegrande/apicognitivas/
│   │   ├── config/          # Configuraciones (CORS, WebClient, OpenAPI)
│   │   ├── model/           # Entidades y DTOs
│   │   ├── repository/      # Repositorios MongoDB Reactive
│   │   ├── rest/            # Controladores REST
│   │   └── service/         # Lógica de negocio
│   └── resources/
│       ├── application.yml  # Configuración de la aplicación
│       └── docs/           # Documentación adicional
```

## **Autor**

Desarrollado por **María Sulca** - [MariaSulcaDev](https://github.com/MariaSulcaDev)

---

*Este proyecto es parte del aprendizaje en el desarrollo de microservicios reactivos con Spring Boot y la integración de servicios cognitivos externos.*
