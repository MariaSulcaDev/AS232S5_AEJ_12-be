# API de Video Downloader

Esta API permite obtener detalles y enlaces de descarga de videos de YouTube utilizando el servicio de RapidAPI.

## Endpoints Disponibles

### 1. Obtener Detalles de Video

**POST** `/api/cognitive/video-details`

Obtiene los detalles de un video de YouTube, incluyendo títulos, descripción, thumbnails, enlaces de video y subtítulos.

#### Request Body

```json
{
  "videoId": "sqgSm8fWe1s",
  "urlAccess": "normal",
  "videos": "auto",
  "audios": "auto"
}
```

#### Parámetros

- `videoId` (requerido): ID del video de YouTube
- `urlAccess` (opcional): Tipo de acceso a la URL (default: "normal")
- `videos` (opcional): Calidad de video solicitada (default: "auto")
- `audios` (opcional): Calidad de audio solicitada (default: "auto")

#### Response

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
  "lengthText": "1:30",
  "viewCountText": "1M views",
  "publishedTimeText": "1 day ago",
  "video": {
    "url": "https://...",
    "quality": "720p",
    "sizeText": "10MB",
    "mimeType": "video/mp4"
  },
  "hasSubtitles": true
}
```

### 2. Obtener Todos los Video Downloads

**GET** `/api/cognitive/video-downloads`

Obtiene una lista de todos los video downloads almacenados en la base de datos.

#### Response

```json
[
  {
    "id": "64f...",
    "createdAt": "2024-01-01T10:00:00",
    "externalEndpoint": "https://youtube-media-downloader.p.rapidapi.com/v2/video/details?videoId=sqgSm8fWe1s&videos=auto",
    "localEndpoint": "/api/cognitive/video-details",
    "request": {
      "videoId": "sqgSm8fWe1s",
      "urlAccess": "normal",
      "videos": "auto",
      "audios": "auto"
    },
    "response": {
      // Respuesta completa de la API
    }
  }
]
```

### 3. Obtener Video Download por ID

**GET** `/api/cognitive/video-downloads/{id}`

Obtiene un video download específico por su ID.

#### Parámetros

- `id`: ID único del registro en la base de datos

#### Response

```json
{
  "id": "64f...",
  "createdAt": "2024-01-01T10:00:00",
  "externalEndpoint": "https://youtube-media-downloader.p.rapidapi.com/v2/video/details?videoId=sqgSm8fWe1s&videos=auto",
  "localEndpoint": "/api/cognitive/video-details",
  "request": {
    "videoId": "sqgSm8fWe1s",
    "urlAccess": "normal",
    "videos": "auto",
    "audios": "auto"
  },
  "response": {
    // Respuesta completa de la API
  }
}
```

## Características Especiales

### Procesamiento de Calidad

- **Channel Avatar**: Se selecciona automáticamente la imagen con mayor dimensión
- **Thumbnails**: Se ordenan por calidad, priorizando las de mayor resolución
- **Videos**: Se filtran y ordenan por calidad, excluyendo los audios según especificación
- **Subtítulos**: Se preservan todos los subtítulos disponibles

### Almacenamiento

Cada solicitud se almacena en MongoDB con:

- Timestamp de creación
- Endpoint externo utilizado
- Endpoint local
- Request original
- Response procesada

### Configuración

La API utiliza las siguientes configuraciones en `application.yml`:

```yaml
youtube:
  api:
    url: https://youtube-media-downloader.p.rapidapi.com/v2/video/details
    key: ${RAPIDAPI_KEY}
    host: youtube-media-downloader.p.rapidapi.com
```

## Códigos de Error

- **200**: Éxito
- **400**: Error en la solicitud (parámetros inválidos)
- **404**: Video no encontrado o ID no existe
- **500**: Error interno del servidor

## Ejemplo de Uso

```bash
# Obtener detalles de un video
curl -X POST http://localhost:8081/api/cognitive/video-details \
  -H "Content-Type: application/json" \
  -d '{
    "videoId": "sqgSm8fWe1s",
    "videos": "auto"
  }'

# Obtener todos los video downloads
curl -X GET http://localhost:8081/api/cognitive/video-downloads

# Obtener un video download específico
curl -X GET http://localhost:8081/api/cognitive/video-downloads/64f...
```
