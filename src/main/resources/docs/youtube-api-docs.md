# API YouTube Media Downloader

## Descripción

API reactiva para obtener información y enlaces de descarga de videos de YouTube usando Spring Boot WebFlux y MongoDB reactivo.

## Configuración

### Variables de entorno en application.yml

```yaml
youtube:
  api:
    url: https://youtube-media-downloader.p.rapidapi.com/v2/video/details
    key: tu-rapidapi-key-aqui
    host: youtube-media-downloader.p.rapidapi.com
```

## Endpoints Disponibles

### 1. Obtener detalles completos del video

**POST** `/api/youtube/video-details`

**Request Body:**

```json
{
  "videoId": "sqgSm8fWe1s",
  "urlAccess": "normal",
  "videos": "auto",
  "audios": "auto"
}
```

**Headers:**

```
User-Identifier: usuario123
Content-Type: application/json
```

### 2. Obtener enlaces de descarga filtrados (1080p + 2 calidades adicionales)

**POST** `/api/youtube/video-downloads`

**Request Body:**

```json
{
  "videoId": "sqgSm8fWe1s"
}
```

**Response:** Videos filtrados por calidad 1920x1080, 720p y 480p únicamente.

### 3. Respuesta simplificada

**POST** `/api/youtube/video-simple`

**Request Body:**

```json
{
  "videoId": "sqgSm8fWe1s"
}
```

**Response:**

```json
{
  "videoId": "sqgSm8fWe1s",
  "title": "Demon Slayer: Kimetsu no Yaiba Castillo Infinito l Tráiler Doblado",
  "description": "Lo que viene superará todo lo que imaginaste...",
  "channelName": "Canal Ejemplo",
  "lengthSeconds": 90,
  "viewCount": 113207,
  "likeCount": 6678,
  "quality1080p": "https://url-video-1080p.com",
  "availableQualities": [
    {
      "url": "https://url-video.com",
      "quality": "1080p",
      "width": 1920,
      "height": 1080,
      "size": 19035963,
      "sizeText": "18.2MB",
      "hasAudio": false,
      "mimeType": "video/mp4",
      "extension": "mp4"
    }
  ],
  "thumbnailUrl": "https://thumbnail-url.com",
  "publishedTimeText": "Sep 1, 2025"
}
```

### 4. Buscar video por ID (desde base de datos)

**GET** `/api/youtube/video/{videoId}`

### 5. Obtener información rápida (BD primero, luego API)

**GET** `/api/youtube/video/{videoId}/quick`

**Headers:**

```
User-Identifier: usuario123
```

### 6. Historial de consultas de usuario

**GET** `/api/youtube/queries/user/{userIdentifier}`

### 7. Historial de consultas de video

**GET** `/api/youtube/queries/video/{videoId}`

## Filtros de Calidad

La API filtra automáticamente los videos por:

1. **1920x1080 (1080p)** - Resolución específica requerida
2. **720p** - Calidad adicional 1
3. **480p** - Calidad adicional 2

## Modelos de Datos

### VideoDetails (MongoDB Document)

```javascript
{
  "_id": "ObjectId",
  "errorId": "Success",
  "type": "video",
  "videoId": "sqgSm8fWe1s",
  "title": "Título del video",
  "description": "Descripción del video",
  "channel": {
    "id": "canal-id",
    "name": "Nombre del canal",
    "handle": "@canal",
    "avatar": "url-avatar",
    "isVerified": true,
    "subscriberCountText": "1M suscriptores"
  },
  "lengthSeconds": 90,
  "viewCount": 113207,
  "likeCount": 6678,
  "publishedTime": "2025-09-01T10:32:26-07:00",
  "publishedTimeText": "Sep 1, 2025",
  "isLiveStream": false,
  "isLiveNow": false,
  "isRegionRestricted": false,
  "isUnlisted": false,
  "isCommentDisabled": false,
  "commentCountText": "363",
  "thumbnails": [...],
  "musicCredits": [...],
  "videos": {
    "errorId": "Success",
    "expiration": 1756966994,
    "items": [...]
  },
  "createdAt": "2025-09-03T...",
  "updatedAt": "2025-09-03T..."
}
```

### YouTubeQuery (MongoDB Document)

```javascript
{
  "_id": "ObjectId",
  "videoId": "sqgSm8fWe1s",
  "originalRequest": "request-data",
  "response": "response-data",
  "status": "SUCCESS", // SUCCESS, ERROR, PENDING
  "requestTime": "2025-09-03T...",
  "responseTime": "2025-09-03T...",
  "userIdentifier": "usuario123",
  "errorMessage": null
}
```

## Ejemplo de Uso con cURL

```bash
# Obtener detalles completos
curl -X POST "http://localhost:8081/api/youtube/video-details" \
     -H "Content-Type: application/json" \
     -H "User-Identifier: usuario123" \
     -d '{"videoId": "sqgSm8fWe1s"}'

# Obtener solo videos filtrados
curl -X POST "http://localhost:8081/api/youtube/video-downloads" \
     -H "Content-Type: application/json" \
     -H "User-Identifier: usuario123" \
     -d '{"videoId": "sqgSm8fWe1s"}'

# Respuesta simplificada
curl -X POST "http://localhost:8081/api/youtube/video-simple" \
     -H "Content-Type: application/json" \
     -H "User-Identifier: usuario123" \
     -d '{"videoId": "sqgSm8fWe1s"}'
```

## Características

- ✅ **Reactivo**: Uso de WebFlux y MongoDB reactivo
- ✅ **Filtrado automático**: Solo 1080p + 2 calidades adicionales
- ✅ **Almacenamiento**: Guarda respuestas en MongoDB
- ✅ **Historial**: Tracking de consultas por usuario y video
- ✅ **Manejo de errores**: Logging y respuestas apropiadas
- ✅ **Respuestas optimizadas**: Endpoints simplificados para frontend
- ✅ **Cache inteligente**: Búsqueda en BD antes de consultar API externa
