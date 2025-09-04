# API de Traducción - Documentación y Ejemplos

## Endpoints Disponibles

### 1. Traducir Texto

**POST** `/api/cognitive/translate`

**Request Body:**

```json
{
    "q": "Hello World!",
    "source": "en",
    "target": "es"
}
```

**Response:**

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

### 2. Obtener todas las traducciones

**GET** `/api/cognitive/translations`

**Response:**

```json
[
    {
        "id": "673d2f1a2b4c5d6e7f8g9h0i",
        "createdAt": "2024-09-03T10:30:45",
        "endpoint": "https://deep-translate1.p.rapidapi.com/language/translate/v2",
        "request": {
            "q": "Hello World!",
            "source": "en",
            "target": "es"
        },
        "response": {
            "data": {
                "translations": {
                    "translatedText": [
                        "¡Hola Mundo!"
                    ]
                }
            }
        }
    }
]
```

### 3. Obtener traducción por ID

**GET** `/api/cognitive/translations/{id}`

**Response:**

```json
{
    "id": "673d2f1a2b4c5d6e7f8g9h0i",
    "createdAt": "2024-09-03T10:30:45",
    "endpoint": "https://deep-translate1.p.rapidapi.com/language/translate/v2",
    "request": {
        "q": "Hello World!",
        "source": "en",
        "target": "es"
    },
    "response": {
        "data": {
            "translations": {
                "translatedText": [
                    "¡Hola Mundo!"
                ]
            }
        }
    }
}
```

## Códigos de Idioma Soportados

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

## Ejemplos de Uso con cURL

### Traducir de Inglés a Español

```bash
curl -X POST http://localhost:8081/api/cognitive/translate \
-H "Content-Type: application/json" \
-d '{
    "q": "Hello, how are you?",
    "source": "en",
    "target": "es"
}'
```

### Traducir de Español a Inglés

```bash
curl -X POST http://localhost:8081/api/cognitive/translate \
-H "Content-Type: application/json" \
-d '{
    "q": "Hola, ¿cómo estás?",
    "source": "es",
    "target": "en"
}'
```

### Obtener todas las traducciones

```bash
curl -X GET http://localhost:8081/api/cognitive/translations
```

### Obtener traducción específica

```bash
curl -X GET http://localhost:8081/api/cognitive/translations/673d2f1a2b4c5d6e7f8g9h0i
```

## Configuración de Variables de Entorno

Para usar esta API, necesitas configurar las siguientes variables:

1. **RAPIDAPI_KEY**: Tu clave de API de RapidAPI para Deep Translate
2. Las configuraciones de MongoDB ya están en el application.yml

## Estructura de la Base de Datos

Cada traducción se guarda en MongoDB con la siguiente estructura:

- **id**: ID único de la traducción
- **createdAt**: Fecha y hora de creación
- **endpoint**: URL del servicio de traducción utilizado
- **request**: Objeto con los datos de entrada (texto, idioma origen, idioma destino)
- **response**: Objeto con la respuesta completa del servicio de traducción

## Características

- ✅ Traducción en tiempo real usando RapidAPI Deep Translate
- ✅ Almacenamiento automático de solicitudes y respuestas
- ✅ API reactiva con WebFlux
- ✅ Persistencia en MongoDB
- ✅ Logging completo de operaciones
- ✅ Manejo de errores
- ✅ CORS habilitado para frontend
- ✅ Configuración mediante variables de entorno
